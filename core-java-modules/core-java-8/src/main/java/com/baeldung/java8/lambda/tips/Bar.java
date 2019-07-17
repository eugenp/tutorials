package com.baeldung.java8.lambda.tips;


@FunctionalInterface
public interface Bar {

    String method(String string);

    default String defaultMethod() {
        return "String from Bar";
    }

}
