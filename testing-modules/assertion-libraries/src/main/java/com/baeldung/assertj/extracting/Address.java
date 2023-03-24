package com.baeldung.assertj.extracting;

class Address {
    private String street;
    private String city;
    private ZipCode zipCode;

    Address(String street, String city, ZipCode zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }
}
