package com.baeldung.hexagonal.domain;

import java.time.ZonedDateTime;

public class Transaction {

    private ZonedDateTime timestamp;
    private int value;
    private String fromAccount;
    private String toAccount;
    private String currency;
    private String description;

    public Transaction() {

    }

    public Transaction(ZonedDateTime timestamp, int value, String fromAccount,  String toAccount, String currency, String description) {

        this.timestamp = timestamp;
        this.value = value;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.currency = currency;
        this.description = description;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " from: " + fromAccount + " to: " + toAccount + " " + value + " " + currency + " desc: " + description;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
