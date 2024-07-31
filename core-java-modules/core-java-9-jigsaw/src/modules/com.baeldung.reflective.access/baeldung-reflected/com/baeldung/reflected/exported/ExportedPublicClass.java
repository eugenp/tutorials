package com.baeldung.reflected.exported;

public class ExportedPublicClass {
    public static void testPublicStaticMethod() {
        System.out.println("ExportedPublicClass.testPublicStaticMethod()");
    }

    private static void testPrivateStaticMethod() {
        System.out.println("ExportedPublicClass.testPrivateStaticMethod()");
    }
}