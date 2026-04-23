package com.baeldung.FixingJava.io.NotSerializableException;

import java.io.Serializable;

public class Address implements Serializable {

    private String city;
    private String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}
