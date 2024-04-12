package com.baeldung.readonlytransactions.mysql.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReadOnlyInterception {

    private static final Logger logger = LoggerFactory.getLogger(ReadOnlyInterception.class);

    @Around("@annotation(com.baeldung.readonlytransactions.mysql.spring.ReaderDS)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            ReadOnlyContext.enter();
            //Debug data source switch
            logger.debug("-----------------------------Entering read only zone-----------------------------");
            return joinPoint.proceed();
        } finally {
            logger.debug("-----------------------------Leaving read only zone------------------------------");
            ReadOnlyContext.exit();
        }
    }

}
