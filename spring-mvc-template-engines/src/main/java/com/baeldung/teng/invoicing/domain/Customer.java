package com.baeldung.teng.invoicing.domain;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Customer {

    private final String vatNumber;

    private final String firstName;

    private final String lastName;

    public Customer(String vatNumber, String firstName, String lastName) {
        this.vatNumber = requireNonNull(vatNumber);
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
    }

    public String getVatNumber() { return vatNumber; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    @Override
    public String toString() { return "#" + vatNumber + " " + lastName + " " + firstName; }

    @Override
    public boolean equals(Object o) {
        return this == o ||
               o != null && o.getClass() == getClass() && Objects.equals(((Customer) o).vatNumber, vatNumber);
    }

    @Override
    public int hashCode() { return Objects.hash(vatNumber); }
}
