package com.baeldung.multiparameterconstructor;

import com.fasterxml.jackson.annotation.JsonCreator;

public record Guest(String firstname, String surname) {

    public Guest(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
        // some validation
    }

    public Guest(String firstname, String surname, int id) {
        this(firstname, surname);
        // some validation
    }

    @JsonCreator
    public static Guest fromJson(String firstname, String surname, int id) {
        // some validation
        return new Guest(firstname, surname);
    }
}
