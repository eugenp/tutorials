package com.baeldung.gson.multiplefields;

public class StudentV1 {

    private String firstName;
    private String lastName;

    // Default constructor for Gson
    public StudentV1() {

    }

    public StudentV1(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
