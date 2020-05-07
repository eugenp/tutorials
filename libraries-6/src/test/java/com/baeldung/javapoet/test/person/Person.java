package com.baeldung.javapoet.test.person;

import java.lang.String;

public interface Person {
    String DEFAULT_NAME = "Alice";

    String getName();

    default String getDefaultName() {
        return DEFAULT_NAME;
    }
}
