package com.baeldung.hexagonalarchitecture.business.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {

    private UUID id;
    private String firstName;
    private String lastName;


    @JsonCreator
    public Ticket(@JsonProperty("firstName") final String firstName, @JsonProperty("lastName") final String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
