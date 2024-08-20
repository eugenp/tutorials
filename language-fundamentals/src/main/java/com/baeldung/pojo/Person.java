package com.baeldung.pojo;

class Person implements Cloneable {
    String name;
    Address address;
    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Shallow copy
    }


}