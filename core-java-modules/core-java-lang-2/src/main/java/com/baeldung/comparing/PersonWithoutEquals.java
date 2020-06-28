package com.baeldung.comparing;

public class PersonWithoutEquals {
    private String firstName;
    private String lastName;

    public PersonWithoutEquals(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
