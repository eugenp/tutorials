package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.PrintWriter;
import java.io.StringWriter;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by Tony Shen on 16/3/23.
 * 来自：https://www.programcreek.com/java-api-examples/?code=fengzhizi715/SAF-AOP/SAF-AOP-master/saf-aop/src/main/java/com/safframework/aop/CacheAspect.java#
 */
@Aspect
public class SafeAspect {

    @Around("execution(!synthetic * *(..)) && onSafe()")
    public Object doSafeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return safeMethod(joinPoint);
    }

    @Pointcut("@within(com.safframework.aop.annotation.Safe)||@annotation(com.safframework.aop.annotation.Safe)")
    public void onSafe() {
    }

    private Object safeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            L.w(getStringFromException(e));
        }
        return result;
    }

    private static String getStringFromException(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}