package com.baeldung.nullconversion;

public class Person implements Cloneable {

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    protected Person clone() {
        Person person;
        try {
            person = (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        address = address.clone();
        return person;
    }
}
