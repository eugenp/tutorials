package com.baeldung.hexagonal.domain;
//Domain Object
public class User {
    private int id;
    private String firstName;
    private String lastName;

    // standard constructor and getters

    public User(int id, String firstName, String lastName) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
