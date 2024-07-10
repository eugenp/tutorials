package com.baeldung.bulkandbatchapi.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class Address implements Serializable {

    private int id;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    public Address() {
    }

    public Address(int id, String street, String city) {
        this.id = id;
        this.street = street;
        this.city = city;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
