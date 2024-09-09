package com.baeldung.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect2 {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect2.class);

    @Pointcut("execution(public * com.baeldung.logging.*.*(..))")
    private void publicMethodsFromLoggingPackage() {
    }

    @Before(value = "publicMethodsFromLoggingPackage()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        logger.debug(">> {}() - {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "publicMethodsFromLoggingPackage()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.debug("<< {}() - {}", methodName, result);
    }

    @AfterThrowing(pointcut = "publicMethodsFromLoggingPackage()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("<< {}() - {}", methodName, exception.getMessage());
    }

    @Around(value = "publicMethodsFromLoggingPackage()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        logger.debug(">> {}() - {}", methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
        logger.debug("<< {}() - {}", methodName, result);
        return result;
    }
}
