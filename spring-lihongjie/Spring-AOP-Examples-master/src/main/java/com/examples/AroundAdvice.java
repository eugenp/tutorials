package com.examples;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Aspect
@Order(3)
public class AroundAdvice {
    @Around(value = "execution (* com.examples.Helloworld.*(..))")
    public Object executeAroundAdvice(ProceedingJoinPoint joinpoint) throws Throwable {

        System.out.println("before executing Hello world using around advice");
        long start = System.currentTimeMillis();
        joinpoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("after executing Hello world  using around advice");
        System.out.println("执行时间为: " + (end - start));
        return "";
    }
}