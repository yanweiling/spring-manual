package com.spring;

/**
 * bean的定义
 * 当对类进行解析完成后，将解析结果生成BeanDefinition
 */
public class BeanDefinition {
    //当前bean的类型
    private Class clazz;
    //当前bean 的scope
    private String scope;

    public BeanDefinition(){}

    public BeanDefinition(Class clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }


    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }


    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
