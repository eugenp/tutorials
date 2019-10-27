package org.baeldung.batch.service;

public class NegativeAmountException extends RuntimeException {

    private double amount;

    public NegativeAmountException(double amount){
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
