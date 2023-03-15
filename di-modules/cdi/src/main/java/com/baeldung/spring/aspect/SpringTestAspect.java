package com.baeldung.spring.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class SpringTestAspect {
    @Autowired
    private List<String> accumulator;

    @Around("execution(* com.baeldung.spring.service.SpringSuperService.*(..))")
    public Object auditMethod(ProceedingJoinPoint jp) throws Throwable {
        String methodName = jp.getSignature().getName();
        accumulator.add("Call to " + methodName);
        Object obj = jp.proceed();
        accumulator.add("Method called successfully: " + methodName);
        return obj;
    }
}
