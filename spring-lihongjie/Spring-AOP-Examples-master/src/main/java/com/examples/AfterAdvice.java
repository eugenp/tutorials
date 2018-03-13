package com.examples;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class AfterAdvice {
    @After(value = "execution (* com.examples.Helloworld.*(..))")
    public void executeAfterAdvice() throws Throwable {
        System.out.println("after executing Hello world  using after advice");
    }
}
