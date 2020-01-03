package com.baeldung.reflection.exception.invocationtarget;

import java.lang.reflect.Method;

public class InvocationTargetDemo {
    public static void main(String[] args) throws Throwable {

        try {

            InvocationTargetExample targetExample = new InvocationTargetExample();
            Method method = InvocationTargetExample.class.getMethod("divideByZeroExample");
            method.invoke(targetExample);
        } catch (Exception e) {

            throw e.getCause();
        }
    }
}
