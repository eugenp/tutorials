package com.baeldung.pattern.portsAndAdapters.core.model;

import java.util.Date;

public class Transaction {

    String description;
    double amount;
    Date transactionDate;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.transactionDate = new Date();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
