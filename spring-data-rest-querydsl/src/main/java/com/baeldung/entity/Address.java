package com.baeldung.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity public class Address {

    @Id private String id;
    private String address;
    private String country;

    public Address() {
    }

    public Address(String id, String address, String country) {
        this.id = id;
        this.address = address;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
