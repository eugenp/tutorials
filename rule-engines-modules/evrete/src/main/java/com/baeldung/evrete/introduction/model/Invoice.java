package com.baeldung.evrete.introduction.model;

public class Invoice {
    private final Customer customer;
    private final double amount;

    public Invoice(Customer customer, double amount) {
        this.customer = customer;
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getAmount() {
        return amount;
    }
}
