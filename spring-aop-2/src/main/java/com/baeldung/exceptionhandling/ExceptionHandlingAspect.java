package com.baeldung.exceptionhandling;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @Pointcut("execution(* com.baeldung.exceptionhandling..*(..))")
    public void serviceLayerPointcut() {
        // Pointcut expression
    }

    @AfterThrowing(pointcut = "serviceLayerPointcut()", throwing = "ex")
    public void logAndHandleException(Exception ex) {
        logger.error("Exception occurred: {}", ex.getMessage(), ex);
    }

}
