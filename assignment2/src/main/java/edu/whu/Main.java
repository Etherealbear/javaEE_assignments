package edu.whu;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
    //读取配置文件并创建一个对象
    String prospath = "/myapp.properties";
    PropertiesLoader pl = new  PropertiesLoader();
    Object obj = pl.CreateObjectWithProperties(prospath);

    //如果类有InitMethod注解，则调用其方法
       boolean result = CallAnnotation(obj);
       System.out.println(result);
    }

    //有类注解就调用该方法
    public static boolean CallAnnotation(Object obj) throws InvocationTargetException, IllegalAccessException {
        boolean result = false;
        Method[] methods = obj.getClass().getDeclaredMethods(); //用反射获取类的全部方法
        for(Method m : methods){
            if(m.isAnnotationPresent(InitMethod.class)){ //如果有 InitMethod注解
                m.invoke(obj); //调用其方法
                result = true;
            }
        }
        return result;
    }
}
