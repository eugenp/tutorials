package com.baeldung.springaop.unittest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    private Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    @Around("execution(* com.baeldung.springaop.unittest.ArraySorting.sort(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long t = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        logger.info("Execution time=" + (System.currentTimeMillis() - t) + "ms");
        return result;
    }

}