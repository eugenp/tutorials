package com.baeldung.entity;

public class Address {

    private String street;
    private String postalcode;
    private String county;

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public Address setPostalcode(String postalcode) {
        this.postalcode = postalcode;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public Address setCounty(String county) {
        this.county = county;
        return this;
    }
}
