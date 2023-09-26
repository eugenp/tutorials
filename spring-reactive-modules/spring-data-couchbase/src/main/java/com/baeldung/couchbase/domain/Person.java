package com.baeldung.couchbase.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Document
public class Person {

    @Id private UUID id;
    private String firstName;

    public Person(final UUID id, final String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    private Person() {
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName);
    }
}