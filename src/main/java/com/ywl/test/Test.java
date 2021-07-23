package com.ywl.test;

import com.spring.ManualApplicationContext;
import com.ywl.service.UserService;

public class Test {
    public static void main(String[] args) throws Exception {
        ManualApplicationContext context=new ManualApplicationContext(AppConfig.class);
        UserService userService= (UserService) context.getBean("userService");
        userService.test();


    }
}
