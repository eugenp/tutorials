package com.baeldung;


@FunctionalInterface
public interface Baz {

    String method(String string);

    default String defaultMethod() {
        return "String from Baz";
    }
}
