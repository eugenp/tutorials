package com.baeldung.java9.interfaces;

public interface Foo {

    public default void bar() {
        System.out.print("Hello");
        baz();
    }

    public static void buzz() {
        System.out.print("Hello");
        staticBaz();
    }

    private void baz() {
        System.out.print(" world!");
    }

    private static void staticBaz() {
        System.out.print(" static world!");
    }
}
