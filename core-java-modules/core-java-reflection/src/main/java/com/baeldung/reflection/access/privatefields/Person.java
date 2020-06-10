package com.baeldung.reflection.access.privatefields;

public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String greet(String name) {
        return "Hello " + name;
    }

    private int divideByZeroExample() {
        return 1 / 0;
    }

}
