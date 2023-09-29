package com.baeldung.compareobjects;

import java.util.Objects;

public class Address {
    private String streetAddress;
    private String city;
    private String postalCode;

    public Address(String streetAddress, String city, String postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Address{" + "streetAddress='" + streetAddress + '\'' + ", city='" + city + '\'' + ", postalCode='" + postalCode + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address) o;
        return Objects.equals(streetAddress, address.streetAddress) && Objects.equals(city, address.city) && Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, city, postalCode);
    }
}
