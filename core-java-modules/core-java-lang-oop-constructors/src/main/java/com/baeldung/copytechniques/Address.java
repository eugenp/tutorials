package com.baeldung.copyconstructor;

public class Address {

    public String address1;

    public Address(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }
}