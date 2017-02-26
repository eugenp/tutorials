package com.baeldung.beaninjection.domain;

public class Transaction {
    private String description;

    public Transaction(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return String.format("%s", description);
    }
}
