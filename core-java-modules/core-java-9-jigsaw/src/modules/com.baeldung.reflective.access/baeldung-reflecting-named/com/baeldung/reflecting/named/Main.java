package com.baeldung.reflecting.named;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.baeldung.reflected.internal.InternalNonPublicClass");
        Method method = clazz.getDeclaredMethod("testPrivateStaticMethod");
        method.setAccessible(true);
        method.invoke(null);
    }
}