package com.baeldung.dip.entities;

public class Customer {

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer{" + "name=" + name + '}';
    }
}
