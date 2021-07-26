package com.ywl.service;

import com.spring.BeanPostProcessor;
import com.spring.Compont;

@Compont
public class MyBeanPostProcessor  implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前....");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后....");
        return bean;
    }
}
