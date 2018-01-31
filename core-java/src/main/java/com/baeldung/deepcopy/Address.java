package com.baeldung.deepcopy;

import java.io.Serializable;

class Address implements Serializable, Cloneable {

    private static final long serialVersionUID = 1740913841244949416L;

    private String street;
    private String city;
    private String country;

    public Address(Address that) {
        this(that.getStreet(), that.getCity(), that.getCountry());
    }

    public Address(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
