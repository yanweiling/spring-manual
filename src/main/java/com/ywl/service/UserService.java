package com.ywl.service;

import com.spring.Autowire;
import com.spring.BeanNameAware;
import com.spring.Compont;

@Compont("userService")
//@Scope("prototype")//表示是一个原型bean，每次get 这个bean的时候每次都不一样
public class UserService implements BeanNameAware {

    @Autowire
    private OrderService orderService;

    private String beanName;

    public void test(){
        System.out.println(orderService);
        System.out.println(beanName);
    }

    @Override
    public void setBeanName(String name) {

        beanName=name;
    }
}
