package edu.whu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//基于配置文件创建对象的方法
//需要输入配置文件的地址
//返回值的是对象类型
public class PropertiesLoader {
    public Object CreateObjectWithProperties(String prospath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties pros = new Properties();
        try(InputStream input = PropertiesLoader.class.getResourceAsStream(prospath)){
            if(input == null){return null;}
            pros.load(input);
            //读取配置文件键值对
        }
        catch (IOException e){
            System.out.println("Load proerties error!");
        }

        //利用反射机制创建新对象
        String classname = pros.getProperty("bootstrapClass");
        Class myClass = Class.forName(classname);//先获取类
        Object obj = myClass.newInstance();
        return obj;
    }
}