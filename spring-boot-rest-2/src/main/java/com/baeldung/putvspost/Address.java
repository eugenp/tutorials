package com.baeldung.putvspost;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {

    private @Id @GeneratedValue Long id;
    private String name;
    private String city;
    private String postalCode;

    Address() {
    }

    public Address(String name, String city, String postalCode) {
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Address [id=" + id + ", name=" + name + ", city=" + city + ", postalCode=" + postalCode + "]";
    }

}
