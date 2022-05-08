package com.baeldung.reflected.internal;

public class InternalPublicClass {
    public static void testPublicStaticMethod() {
        System.out.println("InternalPublicClass.testPublicStaticMethod()");
    }

    private static void testPrivateStaticMethod() {
        System.out.println("InternalPublicClass.testPrivateStaticMethod()");
    }
}