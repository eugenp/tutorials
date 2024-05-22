package com.baeldung.deepcopyshallowcopy;

import java.lang.Cloneable;

public class Address implements Cloneable {
    String street;
    int houseNumber;

    public Address(String street, int houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}