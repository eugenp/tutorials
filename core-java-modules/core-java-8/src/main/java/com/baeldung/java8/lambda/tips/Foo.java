package com.baeldung.java8.lambda.tips;


@FunctionalInterface
public interface Foo {

    String method(String string);

    default void defaultMethod() {
    }
}
