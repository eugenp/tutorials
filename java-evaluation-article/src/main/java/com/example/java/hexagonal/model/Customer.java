package com.example.java.hexagonal.model;

import java.util.Objects;

public class Customer {

    private long id;

    private String firstName;

    // standard setters and getters

    public Customer(long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getId() == customer.getId() &&
                getFirstName().equals(customer.getFirstName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
