package com.baeldung.jersey.exceptionhandling.data;

import com.baeldung.jersey.exceptionhandling.repo.Identifiable;

public class Wallet implements Identifiable {
    private static final long serialVersionUID = 1L;

    public static final Double MIN_CHARGE = 50.0;
    public static final String MIN_CHARGE_MSG = "minimum charge is: " + MIN_CHARGE;

    private String id;
    private Double balance = 0.0;

    public Wallet() {
    }

    public Wallet(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double addBalance(Double amount) {
        if (balance == null)
            balance = 0.0;

        return balance += amount;
    }

    public boolean hasFunds(Double amount) {
        if (balance == null || amount == null) {
            return false;
        }

        return (balance - amount) >= 0;
    }
}
