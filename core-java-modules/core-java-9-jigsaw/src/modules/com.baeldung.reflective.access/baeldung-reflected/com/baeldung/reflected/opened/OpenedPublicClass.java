package com.baeldung.reflected.opened;

public class OpenedPublicClass {
    public static void testPublicStaticMethod() {
        System.out.println("OpenedPublicClass.testPublicStaticMethod()");
    }

    private static void testPrivateStaticMethod() {
        System.out.println("OpenedPublicClass.testPrivateStaticMethod()");
    }
}
