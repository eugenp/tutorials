package com.baeldung.spring.data.noconverterfound.models;

public class EmployeeFullName {

    private String firstName;
    private String lastName;

    public EmployeeFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String fullName() {
        return getFirstName().concat(" ")
            .concat(getLastName());
    }

}
