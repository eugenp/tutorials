package com.baeldung;


@FunctionalInterface
public interface Bar {

    String method(String string);

    default String defaultMethod() {
        return "String from Bar";
    }

}
