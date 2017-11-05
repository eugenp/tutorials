package com.baeldung.setterdi;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {
    private Address address;

    @Autowired
    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }
}
