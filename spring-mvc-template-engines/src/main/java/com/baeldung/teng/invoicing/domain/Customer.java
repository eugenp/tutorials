package com.baeldung.teng.invoicing.domain;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Customer {

    private final String vatNumber;

    private final String firstName;

    private final String lastName;

    public Customer(String firstName, String lastName) { this(null, firstName, lastName); }

    public Customer(String vatNumber, String firstName, String lastName) {
        this.vatNumber = vatNumber;
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
    }

    public String getVatNumber() { return vatNumber; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    @Override
    public String toString() {
        final String fullName = lastName + " " + firstName;
        return vatNumber == null ? fullName : vatNumber + " " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
               o != null && o.getClass() == getClass() && Objects.equals(((Customer) o).vatNumber, vatNumber);
    }

    @Override
    public int hashCode() { return Objects.hash(vatNumber); }
}
