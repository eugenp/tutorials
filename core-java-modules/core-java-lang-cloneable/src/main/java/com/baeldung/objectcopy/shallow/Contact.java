package com.baeldung.objectcopy.shallow;

public class Contact implements Cloneable {
    private Address address;

    public Contact(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact clone() throws CloneNotSupportedException {
        return new Contact(this.getAddress());
    }
}