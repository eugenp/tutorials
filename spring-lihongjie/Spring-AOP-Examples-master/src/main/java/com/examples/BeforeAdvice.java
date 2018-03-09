package com.examples;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class BeforeAdvice {
    @Before(value = "execution (* com.examples.Helloworld.*(..))")
//    @Before(value = "execution (* com.examples.Helloworld.printHelloWorld(..))")
    public void executeBeforeAdvice() throws Throwable {
        System.out.println("after executing Hello world  using before advice");
    }
}