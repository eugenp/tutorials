package com.baeldung.mockito.utils;

public class ClassWithConstructor {
    private String name;

    public ClassWithConstructor(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
