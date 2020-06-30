package com.baeldung.hexagonalarchitecture.business.entities;

import java.util.Objects;
import java.util.StringJoiner;
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

    private Ticket() {
        this.id = UUID.randomUUID();
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

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        final Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
            Objects.equals(firstName, ticket.firstName) &&
            Objects.equals(lastName, ticket.lastName);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ticket.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .toString();
    }
}
