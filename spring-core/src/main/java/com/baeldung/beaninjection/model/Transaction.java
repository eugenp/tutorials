package com.baeldung.beaninjection.model;

public class Transaction {

    private Currency currency;

    public Transaction() {
    }

    public Transaction(Currency currency) {
        this.currency = currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void printSummary() {
        System.out.println("Currency Code: " + this.currency.getCode());
        System.out.println("USD Exchange rate used: " + this.currency.getUSDExchangeRate());
    }
}
