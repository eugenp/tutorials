package com.baeldung.deepshallowcopy;

import java.io.Serializable;

public class Address implements Serializable, Cloneable {
    private String city;
    private String country;
    private int postcode;

    public Address(String city, String country, int postcode) {
        this.city = city;
        this.country = country;
        this.postcode = postcode;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // getters and setters


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

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

}
