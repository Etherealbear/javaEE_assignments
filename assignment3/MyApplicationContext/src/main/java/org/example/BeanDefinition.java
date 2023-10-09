package org.example;

import java.util.*;

// 定义一个Bean类，用于封装配置信息
public class BeanDefinition {
    // Bean的id
    private String id;
    // Bean的类名
    private String className;
    // Bean的属性集合
    //private Properties properties;
    private Map<String,String > properties = new HashMap<>();
    // 构造方法用default的
    private List<Map<Class<?>, Object>> constructorArgs = new ArrayList<>();

    // get和set方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    public List<Map<Class<?>, Object>> getConstructorArgs() {
        return constructorArgs;
    }
}
