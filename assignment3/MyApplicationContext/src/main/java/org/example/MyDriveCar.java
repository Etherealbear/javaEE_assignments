package org.example;

import org.example.Service.DriveCar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyDriveCar {
    //创建一个小的Ioc容器

public static void main(String[] args) throws Exception {
   MyApplicationContext ctx = new MyApplicationContext("applicationContext.xml");
    DriveCar dc = (DriveCar) ctx.getBean("DriveCar");
    dc.running();
}
//    public static void main(String[] args) throws Exception {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        DriveCar dc = (DriveCar) ctx.getBean("DriveCar");
//        dc.running();
//    }
}
