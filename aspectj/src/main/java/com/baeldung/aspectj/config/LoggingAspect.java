package com.baeldung.aspectj.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.baeldung.aspectj..*(..))")
    public void pointcutInsideAspectjPackage(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Executing method inside aspectj package: " + className + "." + methodName);
    }

    @Before("execution(* com.baeldung.aspectj.service..*(..))")
    public void pointcutInsideServicePackage(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Executing method inside service package: " + className + "." + methodName);
    }

    @Before("execution(* com.baeldung.aspectj..*(..)) && !execution(* com.baeldung.aspectj.repository..*(..))")
    public void pointcutWithoutSubPackageRepository(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Executing method without sub-package repository: " + className + "." + methodName);
    }
}