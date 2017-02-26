package com.baeldung.beaninjection.domain;

public class Transaction {
    private String description;
    private double amount;

    public Transaction(String description) {
        this.description = description;
    }
}
