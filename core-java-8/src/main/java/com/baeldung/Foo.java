package com.baeldung;


@FunctionalInterface
public interface Foo {

    String method(String string);

    default void defaultMethod() {
    }
}
