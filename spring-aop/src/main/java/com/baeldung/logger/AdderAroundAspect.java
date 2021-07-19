package com.baeldung.logger;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdderAroundAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Object aroundAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Arguments passed to method are: " + Arrays.toString(joinPoint.getArgs()));
        final Object result = joinPoint.proceed();
        logger.info("Result from method is: " + result);
        return result;
    }
}
