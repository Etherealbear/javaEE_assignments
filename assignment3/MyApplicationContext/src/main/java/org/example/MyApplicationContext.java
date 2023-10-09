package org.example;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.swing.text.Style;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class MyApplicationContext {

    //缓存Spring容器的Bean对象
   private Map<String, Object> beans = new HashMap<String,Object>();
   private Map<String, BeanDefinition> BeanDefinition = new HashMap<>();
    /*利用配置文件初始化当前容器，利用xml配置文件初始化全部的Bean对象*/
   public MyApplicationContext(String xml) throws Exception {
        readConfig(xml);
        createBeans();
        DI();
       }


   public void readConfig(String xml) throws IOException, DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException { //读取xml文件配置信息
       //利用DOM4j，读取xml文件
       //解析XML文件内容，得到Bean的类名和Bean的ID：
       //根据类名动态加载类并且创建对象，
       //将对象和对应的ID添加到map中
       //从Resource(classpath)中读取流
       SAXReader reader = new SAXReader();
       InputStream in = getClass().getClassLoader().getResourceAsStream(xml);
       Document document = reader.read(in);
       //in.close();
       //解析xml
       Element root = document.getRootElement();
       List<Element> list = root.elements("bean");//读取根元素中的全部bean子元素
       for (Element e : list) { //读取每个bean元素的配置信息
           BeanDefinition newbean = new BeanDefinition();
           newbean.setId(e.attributeValue("id"));

           System.out.println(e.attributeValue("id"));

           newbean.setClassName(e.attributeValue("class"));


            List<Element> properties = e.elements("property");
           //读取每个bean元素中的property子元素
           //读取setter类型注入
           for(Element property: properties){
               //判断属性是引用类型还是简单属性类型
               if(property.attributeValue("ref")!=null)
               {
                   String name = property.attributeValue("name");
                   String ref = property.attributeValue("ref");
                   System.out.println("name is "+name);
                   System.out.println("ref is "+ref);
                   //保存bean属性信息
                   newbean.getProperties().put(name,ref);
               }
               if(property.attributeValue("value")!= null){
               //读取简单类型属性注入
               String name = property.attributeValue("name");
               String value = (String) property.attributeValue("value"); //这里有点问题，因为value值可变，待商榷
               System.out.println("name is "+name);
               System.out.println("value is "+value);
               newbean.getProperties().put(name,value);
               }

               //读取构造器注入
               List<Element> contructorElements = e.elements("constructor-arg"); //获取当前bean的构造器标签列表
               for(Element cons : contructorElements){
                   //读取简单类型注入
                   if(cons.attributeValue("type" )== null){
                       // String类型
                       Map<Class<?>,Object> args = new HashMap<>();
                       args.put(String.class,cons.attributeValue("value"));
                       newbean.getConstructorArgs().add(args);
                   }
                   else if (cons.attributeValue("value")!= null){
                       //基本数据类型
                       String type = cons.attributeValue("type");
                       String value = cons.attributeValue("value");
                       Map<Class<?>,Object> args = new HashMap<>();
                       //根据基本数据类型保存bean
                       switch (type){
                           case "int":
                               args.put(Integer.class,Integer.parseInt(value));//将String类型转换成int类型
                               newbean.getConstructorArgs().add(args);
                               break;
                           case "float":
                               args.put(Float.class,Float.parseFloat(value));
                               newbean.getConstructorArgs().add(args);
                               break;
                           case "double":
                               args.put(Double.class,Double.parseDouble(value));
                               newbean.getConstructorArgs().add(args);
                               break;
                           case "short":
                               args.put(Short.class,Short.parseShort(value));
                               newbean.getConstructorArgs().add(args);
                               break;
                           case "long":
                               args.put(Long.class,Long.parseLong(value));
                               newbean.getConstructorArgs().add(args);
                               break;
                           case "boolean":
                               args.put(Boolean.class,Boolean.parseBoolean(value));
                               newbean.getConstructorArgs().add(args);

                           case "char":
                               args.put(Character.class,value.charAt(0));
                               newbean.getConstructorArgs().add(args);
                               break;
                           default:
                               args.put(String.class,value);
                               newbean.getConstructorArgs().add(args);
                       }
                   }
               }
           }
           BeanDefinition.put(newbean.getId(),newbean); //把读取到的每条bean信息保存在Map中
       }
   }

   //根据配置信息用反射创建bean并保存在MAP beans中
    public void  createBeans() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
       //读取之前保存在BeanDefination容器中的bean的配置信息，然后创建bean
        for(String id : BeanDefinition.keySet()){
            //根据id获取每个bean
            BeanDefinition bean = BeanDefinition.get(id);
            //利用反射动态加载类，动态创建对象
            Class<?> cls = Class.forName(bean.getClassName());
            Object beanobj = null;

            //判断是setter注入还是构造函数注入
            //优先进行setter注入
            //目前只实现了setter注入，就只写setter注入

            //获取当前bean的构造器参数
            List<Map<Class<?>,Object>> constructors = bean.getConstructorArgs();
            Class<?>[] constructorArgTypes = new Class<?>[constructors.size()];
            Object[] constructorArgObjects = new Object[constructors.size()];
            for (int i = 0; i < constructors.size(); i++) {
                Iterator iter = constructors.get(i).keySet().iterator();
                Class<?> argClass = (Class<?>) iter.next();
                constructorArgTypes[i] = argClass;
                constructorArgObjects[i] = constructors.get(i).get(argClass);
            }


            if (constructors.size()== 0){
            beanobj = cls.newInstance();
            }
            else {
                //构造器注入
                Constructor<?> constructor = cls.getDeclaredConstructor(constructorArgTypes); //先调用构造函数
                beanobj = cls.newInstance();
            }
            beans.put(id, beanobj);//放到beans中
        }
    }

    //根据配置信息利用反射实现bean的依赖注入
    public void DI() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
       //读取bean的配置信息
        for(String id: BeanDefinition.keySet()){

            BeanDefinition bean = BeanDefinition.get(id); //获取对应bean的配置信息
            Object obj = beans.get(id); //获取对应的bean对象
            //利用反射要先获取类
            Class<?> cls = obj.getClass(); //获取对应类的class

            //根据配置信息的properties实现依赖注入
            //这里只实现了引用注入
            for(String name: bean.getProperties().keySet()){
                String temp = bean.getProperties().get(name);
                System.out.println(temp);
                Field field = cls.getDeclaredField(name); //反射获取属性
                field.setAccessible(true);//打破私有
                if(getBean(temp)!= null){
                field.set(obj,getBean(temp)); //设置引用属性
                     }
               // field.set(obj,temp);
            }
        }
    }

       public Object getBean (String id){
           //根据id在bean中查找并返回对象
           return beans.get(id);
       }
       //使用泛型减少类型转换次数
       public <T> T getBean(String id, Class < T > cls) {
           return (T) beans.get(id);
       }
   }





