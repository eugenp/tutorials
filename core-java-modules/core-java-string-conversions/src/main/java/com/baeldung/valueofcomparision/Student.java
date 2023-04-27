package com.baeldung.valueofcomparision;

public class Student {

    public String name;
    public int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student(" + name + ", age " + age + ')';
    }
}
