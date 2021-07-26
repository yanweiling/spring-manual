package com.spring;

public interface BeanPostProcessor {
    /**
     * 初始化前
     */
    Object postProcessBeforeInitialization(Object bean,String beanName);

    Object postProcessAfterInitialization(Object bean,String beanName);
}
