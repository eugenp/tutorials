package com.baeldung.eclipsecollections;

import java.util.List;

public class Student {

    private String firstName;
    private String lastName;
    private List<String> addresses;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, List<String> addresses) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = addresses;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}