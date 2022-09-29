package com.baeldung.deepcopy2;

import java.io.Serializable;

public class Payment implements Cloneable, Serializable {

    public double amount;
    public String sku;
    public Customer customer;

    public Payment(double amount, String sku, Customer customer) {
        this.amount = amount;
        this.sku = sku;
        this.customer = customer;
    }
    // copy-constructor
    public Payment(Payment payment) {
        this.amount = payment.amount;
        this.sku = payment.sku;
        this.customer = new Customer(payment.customer.username, payment.customer.email);

    }

    @Override
    public Payment clone() {
        try {
            return (Payment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
