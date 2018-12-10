package com.baeldung.performancetests.model.source;

import com.googlecode.jmapper.annotations.JGlobalMap;

public class Address {
    private String street;
    private String city;
    private String postalCode;

    public Address() {
    }

    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Address(String street, String city, String postalCode, String country) {

        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
}
