package com.baeldung.jackson.inclusion.jsonignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Person {

    @JsonIgnore
    private UUID id;
    private String firstName;
    private String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.id = UUID.randomUUID();
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

    public UUID getId() {
        return id;
    }
}
