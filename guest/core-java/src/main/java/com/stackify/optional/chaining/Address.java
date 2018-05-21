package com.stackify.optional.chaining;

import java.util.Optional;

public class Address {
    private String addressLine;
    private String city;
    private Country country;

    public Address(String addressLine, String city) {
        this.addressLine = addressLine;
        this.city = city;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Optional<Country> getCountry() {
        return Optional.ofNullable(country);
    }

    public void setCountry(Country country) {
        this.country = country;
    }   
}
