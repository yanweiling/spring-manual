package com.ywl.test;

import com.spring.ManualApplicationContext;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        ManualApplicationContext context=new ManualApplicationContext(AppConfig.class);
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));

    }
}
