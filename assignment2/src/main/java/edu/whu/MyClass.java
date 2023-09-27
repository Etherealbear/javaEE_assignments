package edu.whu;

public class MyClass {

    private  String sex_type = "WOMAN";
    //InitMethod 注解的方法
    @InitMethod
    public void init(){
        sex_type = "MAN";
    }
}
