package com.baeldung.api;

import java.io.Serializable;

public class Address implements Serializable{

    private String address;
    private String countryCode;

    public Address(String address, String countryCode) {
        this.address = address;
        this.countryCode = countryCode;
    }

    public String getAddress() {
        return address;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override public String toString() {
        return address + " (" + countryCode + ")";
    }
}
