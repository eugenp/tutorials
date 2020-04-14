package com.baeldung.jdbc;

public class Employee {
    private int id;

    private String firstName;

    private String lastName;

    private String address;

    public Employee(int id, String firstName, String lastName, String address) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

}
