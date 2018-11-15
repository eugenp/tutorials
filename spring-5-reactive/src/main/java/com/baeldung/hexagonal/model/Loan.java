package com.baeldung.hexagonal.model;

public class Loan {
    private double amount;
    private double rate;
    private int numberOfMonths;
    private double interest;


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}