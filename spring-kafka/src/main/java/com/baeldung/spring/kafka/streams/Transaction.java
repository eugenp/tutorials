package com.baeldung.spring.kafka.streams;

public class Transaction {

    private String type;
    private long amount;

    public Transaction(final String type, final long amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(final long amount) {
        this.amount = amount;
    }
}