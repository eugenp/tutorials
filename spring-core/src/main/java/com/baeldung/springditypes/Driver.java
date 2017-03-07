package com.baeldung.springditypes;

/**
 * Created by gerald on 3/6/17.
 */
public class Driver {
    private String name;
    private int age;

    public Driver(String name, int age) {
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
