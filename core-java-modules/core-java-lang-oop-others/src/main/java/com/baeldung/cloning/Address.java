package com.baeldung.cloning;

import java.io.Serializable;

public class Address implements Cloneable, Serializable {
    private String state;
    private String city;
    private String country;

    public Address(String state, String city, String country) {
        this.state = state;
        this.city = city;
        this.country = country;
    }

    @Override
    public Object clone() {
        try {
            return (Address) super.clone();
        } catch (CloneNotSupportedException ex) {
            return new Address(this.state, this.getCity(), this.getCountry());
        }
    }

    public Address(Address address) {
        this(address.getState(), address.getCity(), address.getCountry());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
