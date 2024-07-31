package com.baeldung.printnullvalues;

import java.util.Objects;
import java.util.Optional;

public class Employee {
    private String name;
    private int age;
    private String department;

    public Employee(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }

    public String toStringUsingNullCheck() {
        return "Name: " + (name != null ? name : "Unknown") +
                ", Age: " + age +
                ", Department: " + (department != null ? department : "Unknown");
    }

    public String toStringUsingOptional() {
        return "Name: " + Optional.ofNullable(name).orElse("Unknown") +
                ", Age: " + age +
                ", Department: " + Optional.ofNullable(department).orElse("Unknown");
    }

    private String getDefaultIfNull(String value, String defaultValue) {
        return value != null ? value : defaultValue;
    }

    public String toStringUsingCustomHelper() {
        return "Name: " + getDefaultIfNull(name, "Unknown") +
                ", Age: " + age +
                ", Department: " + getDefaultIfNull(department, "Unknown");
    }

    public String toStringUsingObjects() {
        return "Name: " + Objects.toString(name, "Unknown") +
                ", Age: " + age +
                ", Department: " + Objects.toString(department, "Unknown");
    }
}