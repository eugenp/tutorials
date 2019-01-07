package com.baeldung.domain;

import com.baeldung.annotations.ZipCode;

public class Address {

    @ZipCode
    private String zipCode;
    private String city;
    private String country;

    public Address() {
    }

    public Address(String zipCode, String city, String country) {
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
