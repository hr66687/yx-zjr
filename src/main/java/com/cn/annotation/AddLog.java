package com.cn.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
@Target(ElementType.METHOD)
// 运行时sheng
@Retention(RetentionPolicy.RUNTIME)
public @interface AddLog {

//    String name();

    String value() default "";


}
