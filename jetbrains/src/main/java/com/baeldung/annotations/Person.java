package com.baeldung.annotations;

import org.jetbrains.annotations.Contract;

public class Person {

    String name;

    @Contract("_ -> this")
    Person withName(String name) {
        this.name = name;
        return this;
    }

}
