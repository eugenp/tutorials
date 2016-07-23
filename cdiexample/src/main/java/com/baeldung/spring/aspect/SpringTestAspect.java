package com.baeldung.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class SpringTestAspect {
    @Around("execution(* com.baeldung.spring.service.SpringSuperService.*(..))")
    public Object advice(ProceedingJoinPoint jp) throws Throwable {
        String methodName = jp.getSignature().getName();
        System.out.println("Call to "+methodName);
        Object obj = jp.proceed();
        System.out.println("Method called successfully: "+methodName);
        return obj;
    }
}
