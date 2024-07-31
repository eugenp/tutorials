package com.baeldung.reflected.exported;

class ExportedNonPublicClass {
    public static void testPublicStaticMethod() {
        System.out.println("ExportedNonPublicClass.testPublicStaticMethod()");
    }

    private static void testPrivateStaticMethod() {
        System.out.println("ExportedNonPublicClass.testPrivateStaticMethod()");
    }
}