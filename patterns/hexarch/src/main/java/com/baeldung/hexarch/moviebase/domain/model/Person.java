package com.baeldung.hexarch.moviebase.domain.model;

import lombok.Data;

/**
 * A model for a person. It contains
 * first and last name of a person.
 */
@Data
public class Person {

    private String firstName;

    private String lastName;

    public Person(String name) {
        String[] nameParts = name.split(" ");

        setFirstName(nameParts[0]);
        setLastName(nameParts[1]);
    }

}
