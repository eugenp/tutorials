package com.baeldung.examples.guice;

import com.google.inject.Inject;

public class Employee {

    private String firstName;
    private String lastName;

    @Inject
    public Employee(String firstName) {
        this.firstName = firstName;
        this.lastName = "Default";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
