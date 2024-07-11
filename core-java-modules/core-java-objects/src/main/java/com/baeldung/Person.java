package com.baeldung;

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }

    // Method to create a deep copy of Address
    Address deepCopy() {
        return new Address(this.city);
    }
}

class Person {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Method to create a deep copy of Person
    Person deepCopy() {
        return new Person(this.name, this.address.deepCopy());
    }

    // Method to create a shallow copy
    Person shallowCopy() {
        return new Person(this.name, this.address);
    }
}

