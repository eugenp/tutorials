package com.baeldung.sonarqubeandjacoco.transientorserializable;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private String street;
    private String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
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
}
