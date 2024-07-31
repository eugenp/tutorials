package com.baeldung.gson.multiplefields;

public class StudentV1 {

    private String firstName;
    private String lastName;

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

    // Default constructor for Gson
    public StudentV1() {

    }

    public StudentV1(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
