package com.baeldung.jaxws.server.bottomup.model;

public class Employee {
    private int id;
    private String firstName;

    public Employee() {

    }

    public Employee(int id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
