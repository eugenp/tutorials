package com.baeldung.apachefury.event;

import java.io.Serializable;

public class Address implements Serializable {

    private final String street;
    private final String city;
    private final String zipCode;

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return street.equals(address.street) && city.equals(address.city) && zipCode.equals(address.zipCode);
    }

    @Override
    public int hashCode() {
        return street.hashCode() + city.hashCode() + zipCode.hashCode();
    }

}