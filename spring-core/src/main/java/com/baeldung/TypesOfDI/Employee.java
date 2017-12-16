package com.baeldung;

public class Employee {
    private Address address;

    public Employee() {
        System.out.println("define constructor");
    }

    public Employee(Address address) {
        super();
        this.address = address;
    }

    // getter and setter
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    void show() {
        System.out.println(address.toString());
    }
}
