package com.stackify.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Around("execution(* *(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Starting method execution " + joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        logger.info("Stopping method execution " + joinPoint.getSignature().getDeclaringTypeName() + ":" + joinPoint.getSignature().getName());

        return result;
    }
}
