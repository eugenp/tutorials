package com.baeldung.reflection.access.privatefields;

public class Person {

    private String name = "John";

    private int age = 30;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String greet(String name) {
        return "Hello " + name;
    }

    private int divideByZeroExample() {
        return 1 / 0;
    }

}
