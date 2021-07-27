package com.ywl.test;

import com.spring.ManualApplicationContext;
import com.ywl.service.OrderService;

public class Test {
    public static void main(String[] args) throws Exception {
        ManualApplicationContext context=new ManualApplicationContext(AppConfig.class);
//        UserService userService= (UserService) context.getBean("userService");
//        userService.test();


        OrderService orderService= (OrderService) context.getBean("orderService");
        orderService.test();
    }
}
