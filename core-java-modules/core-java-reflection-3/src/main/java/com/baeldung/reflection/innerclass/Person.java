package com.baeldung.reflection.innerclass;

public class Person {
    String name;
    Address address;

    public Person() {
    }

    public class Address {
        String zip;

        public Address(String zip) {
            this.zip = zip;
        }
    }

    public static class Builder {
    }
}
