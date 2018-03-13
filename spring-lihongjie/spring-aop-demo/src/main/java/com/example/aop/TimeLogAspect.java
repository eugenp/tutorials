package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by 彭鸿儒 on 2017/5/16
 * 邮箱：peng_hongru@163.com
 * 来自: https://www.programcreek.com/java-api-examples/?code=penghongru/Coder/Coder-master/app/src/main/java/com/peng_hongru/coder/aop/aspectj/aspect/DoBackAspectj.java#
 */
@Aspect
public class TimeLogAspect {

    @Pointcut("execution(@com.peng_hongru.coder.aop.aspectj.annotation.TimeLog * *(..))")
    public void methodAnnotated() {

    }

    @Pointcut("execution(@com.peng_hongru.coder.aop.aspectj.annotation.TimeLog *.new(..))")
    public void constructorAnnotated() {

    }

    @Around("methodAnnotated() || constructorAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();//执行原方法
        long cost = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        Logger.d("TimeLog: 类 " + className + " 方法 " + methodName + " 耗时 [" + cost + "ms]");
        /*Log.d("timeLog", "====================方法耗时检测====================");
        Log.d("timeLog", "= 类名 : " + className);
        Log.d("timeLog", "= 方法名: " + methodName);
        Log.d("timeLog", "= 耗时 : [" + cost + "ms]");
        Log.d("timeLog", "====================================================");*/
        return result;
    }


}
