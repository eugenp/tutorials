package com.example.aop;

/**
 *
 * @author jiangkang
 * @date 2017/10/25
 */

@Aspect
public class SafeHandler {

    @Pointcut("@within(com.jiangkang.annotations.Safe) || @annotation(com.jiangkang.annotations.Safe)")
    public void onSafe(){}


    @Around("onSafe()")
    public void handleMethodSafe(ProceedingJoinPoint joinPoint){

        try {
            joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            Log.d(joinPoint.getSignature().getName(), "handleMethodSafe: " + throwable.getMessage());
        }

    }



}
