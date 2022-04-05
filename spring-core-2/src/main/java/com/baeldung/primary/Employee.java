package com.baeldung.primary;

/**
 * Created by Gebruiker on 7/17/2018.
 */
public class Employee {

    private String name;

    public Employee(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                '}';
    }
}
