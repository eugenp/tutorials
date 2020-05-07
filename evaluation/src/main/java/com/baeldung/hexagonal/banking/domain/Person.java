package com.baeldung.hexagonal.banking.domain;

import java.util.Objects;

public class Person extends AccountHolder {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName, int idNumber) {
        super(idNumber, "Consumer");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return super.getIdNumber() == person.getIdNumber() && firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getIdNumber(), firstName, lastName);
    }
}
