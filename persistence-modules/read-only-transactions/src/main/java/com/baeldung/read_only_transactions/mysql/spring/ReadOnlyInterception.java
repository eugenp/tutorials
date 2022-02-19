package com.baeldung.read_only_transactions.mysql.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReadOnlyInterception {

    @Around("@annotation(com.baeldung.read_only_transactions.mysql.spring.ReaderDS)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            ReadOnlyContext.enter();
            //Debug data source switch
            //System.out.println("-----------------------------Entering read only zone-----------------------------");
            return joinPoint.proceed();
        } finally {
            //System.out.println("-----------------------------Leaving read only zone------------------------------");
            ReadOnlyContext.exit();
        }
    }

}
