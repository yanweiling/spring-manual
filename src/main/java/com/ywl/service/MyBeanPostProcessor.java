package com.ywl.service;

import com.spring.BeanPostProcessor;
import com.spring.Compont;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        System.out.println("beanName:"+beanName);
        if("userService".equals(beanName)){
            Object instance = Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("执行代理对象内容。。。"+beanName+"."+method.getName());
                    return method.invoke(bean,args);
                }
            });
            System.out.println("代理对象"+instance);
            return instance;
        }
        if("orderService".equals(beanName)){

            //todo 采用cglib动态代理
        }
        System.out.println("原生对象:"+bean);
        return bean;

    }
}
