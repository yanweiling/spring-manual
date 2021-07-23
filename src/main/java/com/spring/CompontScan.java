package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解用来扫描路径
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //该注解只能写在类上
public @interface CompontScan {
    String value() default "";//让用户可以定义扫描路径
}
