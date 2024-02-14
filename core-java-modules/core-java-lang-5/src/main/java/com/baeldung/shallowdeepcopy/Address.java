package com.baeldung.deepshallowcopy;

public class Address {
    private String country;

    public Address(String country) {
        this.country = country;
    }

    // Setter and Getter
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}