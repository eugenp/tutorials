package com.baeldung.examples.guice;

import com.baeldung.examples.common.Address;
import com.google.inject.Inject;

public class Person {
    private String firstName;

    private String lastName;

    private Address address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    @Inject
    public void setAddress(Address address) {
        this.address = address;
        address.setCity("Default");
    }

}
