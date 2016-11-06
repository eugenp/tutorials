package com.baeldung.spring.jms;

public class Employee {
    private String name;
    private Integer age;

    public Employee(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String toString() {
        return "Employee: name(" + name + "), age(" + age + ")";
    }
}
