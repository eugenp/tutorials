package com.examples.cglib;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by lihongjie on 7/4/17.
 *
 */
public class MethodBefore implements MethodBeforeAdvice {


    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {

        System.out.println("before...");
    }
}
