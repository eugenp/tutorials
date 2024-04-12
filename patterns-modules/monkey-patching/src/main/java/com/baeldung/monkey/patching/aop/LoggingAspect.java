package com.baeldung.monkey.patching.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.baeldung.monkey.patching.converter.MoneyConverter.convertEURtoUSD(..))")
    public void beforeConvertEURtoUSD(JoinPoint joinPoint) {
        System.out.println("Before method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.baeldung.monkey.patching.converter.MoneyConverter.convertEURtoUSD(..))")
    public void afterConvertEURtoUSD(JoinPoint joinPoint) {
        System.out.println("After method: " + joinPoint.getSignature().getName());
    }
}