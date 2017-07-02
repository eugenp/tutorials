package com.baeldung;

@FunctionalInterface
interface Bar {

    String method(String string);

    default String defaultMethod() {
        return "String from Bar";
    }

}
