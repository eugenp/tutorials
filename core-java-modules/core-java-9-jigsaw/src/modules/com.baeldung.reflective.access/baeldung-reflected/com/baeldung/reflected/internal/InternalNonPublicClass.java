package com.baeldung.reflected.internal;

class InternalNonPublicClass {
    public static void testPublicStaticMethod() {
        System.out.println("InternalNonPublicClass.testPublicStaticMethod()");
    }

    private static void testPrivateStaticMethod() {
        System.out.println("InternalNonPublicClass.testPrivateStaticMethod()");
    }
}