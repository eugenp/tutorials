package com.baeldung.pojo;

public class Address {
    String city;

    // Constructor for Shallow Copy of Address class
    Address(String city) {
        this.city = city;
    }

    // Constructor for Deep Copy of Address class
    Address(Address other) {
        this.city = other.city;
    }
}
