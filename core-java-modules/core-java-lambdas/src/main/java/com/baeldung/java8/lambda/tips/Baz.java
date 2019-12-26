package com.baeldung.java8.lambda.tips;


@FunctionalInterface
public interface Baz {

    String method(String string);

    default String defaultMethod() {
        return "String from Baz";
    }
}
