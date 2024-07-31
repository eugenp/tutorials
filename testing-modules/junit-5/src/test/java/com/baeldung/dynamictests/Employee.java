package com.baeldung.dynamictests;

public class Employee {

    private long id;
    private String firstName;

    public Employee(long id) {
        this.id = id;
        this.firstName = "";
    }

    public Employee(long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + "]";
    }
}
