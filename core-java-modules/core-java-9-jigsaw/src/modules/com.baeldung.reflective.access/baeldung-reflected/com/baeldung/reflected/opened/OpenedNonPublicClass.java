package com.baeldung.reflected.opened;

class OpenedNonPublicClass {
    public static void testPublicStaticMethod() {
        System.out.println("OpenedNonPublicClass.testPublicStaticMethod()");
    }

    private static void testPrivateStaticMethod() {
        System.out.println("OpenedNonPublicClass.testPrivateStaticMethod()");
    }
}
