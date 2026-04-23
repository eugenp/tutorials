package com.baeldung.async;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(final Throwable throwable, final Method method, final Object... obj) {
        System.err.println("Async Exception Detected!");
        System.err.println("Exception message - " + throwable.getMessage());
        System.err.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.err.println("Parameter value - " + param);
        }
    }
}

