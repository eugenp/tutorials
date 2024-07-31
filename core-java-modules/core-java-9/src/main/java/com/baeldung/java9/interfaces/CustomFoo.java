package com.baeldung.java9.interfaces;

public class CustomFoo implements Foo {

    public static void main(String... args) {
        Foo customFoo = new CustomFoo();
        customFoo.bar(); // 'Hello world!'
        Foo.buzz(); // 'Hello static world!'
    }
}
