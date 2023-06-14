package com.baeldung.objectcopy.deep;

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

    @Override
    public Contact clone() throws CloneNotSupportedException {
        Address newAddress = new Address(this.getAddress()
            .getAddressLine(), this.getAddress()
            .getPostCode());
        return new Contact(newAddress);
    }
}