package com.baeldung.caching.example;

public class Customer {

    private int id;
    private String name;
    private String address;

    public Customer() {
        super();
    }

    public Customer(final String name, final String address) {
        this.name = name;
        this.address = address;
    }

    //

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setCustomerAddress(final String address) {
        this.address = name + "," + address;
    }

}
