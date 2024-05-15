package com.baeldung.jackson.jsonignorevstransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

class Person implements Serializable {

    @JsonIgnore
    private final Long id;

    private final String firstName;

    private final String lastName;

    public Person(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
