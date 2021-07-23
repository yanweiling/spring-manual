package com.ywl.service;

import com.spring.Autowire;
import com.spring.Compont;
import com.spring.InitializingBean;

@Compont("userService")
//@Scope("prototype")//表示是一个原型bean，每次get 这个bean的时候每次都不一样
public class UserService implements InitializingBean {

    @Autowire
    private OrderService orderService;


    public void test(){
        System.out.println(orderService);

    }


    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("初始化");
    }
}
