package com.ywl.service;

import com.spring.Autowire;
import com.spring.Compont;
import com.spring.InitializingBean;

@Compont("userService")
//@Scope("prototype")//表示是一个原型bean，每次get 这个bean的时候每次都不一样
public class UserServiceImpl implements UserService ,InitializingBean{

    @Autowire
    private OrderService orderService;

    private String otherName;

    @Override
    public void test(){
        System.out.println(orderService);
        System.out.println(otherName);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("自定义初始化。。。。。这里赋值设置otherName");
        this.otherName="哈哈";
    }
}
