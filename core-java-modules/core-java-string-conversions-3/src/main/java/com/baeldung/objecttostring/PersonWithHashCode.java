package com.baeldung.objecttostring;

public class PersonWithHashCode {
    private String name;
    private int age;

    public PersonWithHashCode(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
