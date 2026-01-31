package com.baeldung.model;

/**
 * Employee class represents an employee entity.
 * This class is placed in the package com.company.model,
 * so the directory structure MUST match this package name.
 */
public class Employee {

    private String name;
    private int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getInfo() {
        return "ID: " + id + ", Name: " + name;
    }
}
