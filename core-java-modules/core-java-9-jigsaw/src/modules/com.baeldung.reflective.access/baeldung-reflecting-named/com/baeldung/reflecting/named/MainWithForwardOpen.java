package com.baeldung.reflecting.named;

import com.baeldung.intermedium.ForwardOpen;

import java.lang.reflect.Method;

public class MainWithForwardOpen {
    public static void main(String[] args) throws Exception {
        Class<?> currentClass = Main.class;
        Class<?> clazz = Class.forName("com.baeldung.reflected.internal.InternalNonPublicClass");

        ForwardOpen.addOpens(clazz, currentClass);

        Method method = clazz.getDeclaredMethod("testPrivateStaticMethod");
        method.setAccessible(true);
        method.invoke(null);
    }
}