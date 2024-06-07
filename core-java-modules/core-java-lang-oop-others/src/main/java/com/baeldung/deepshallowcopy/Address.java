package com.baeldung.deepshallowcopy;
class Address {
    String city;
    String street;

    // Constructor to initialize Address object
    Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{city='" + city + "', street='" + street + "'}";
    }
}
