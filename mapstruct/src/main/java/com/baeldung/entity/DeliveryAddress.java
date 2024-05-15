package com.baeldung.entity;

public class DeliveryAddress {

    private String forename;
    private String surname;
    private String street;
    private String postalcode;
    private String county;

    public String getForename() {
        return forename;
    }

    public DeliveryAddress setForename(String forename) {
        this.forename = forename;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public DeliveryAddress setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public DeliveryAddress setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public DeliveryAddress setPostalcode(String postalcode) {
        this.postalcode = postalcode;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public DeliveryAddress setCounty(String county) {
        this.county = county;
        return this;
    }
}
