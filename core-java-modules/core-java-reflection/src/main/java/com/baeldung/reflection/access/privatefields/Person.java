package com.baeldung.reflection.access.privatefields;

public class Person {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String greet(String number) {
        return "Hi " + Integer.parseInt(number);
    }
}
