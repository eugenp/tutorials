package com.stackify.optional;

public class Address {
    private String addressLine;
    private String city;
    private Country country;

    public Address(String addressLine, String city, Country country) {
        super();
        this.addressLine = addressLine;
        this.city = city;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

   
}
