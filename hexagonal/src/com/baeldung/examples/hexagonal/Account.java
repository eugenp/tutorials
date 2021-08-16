package com.baeldung.examples.hexagonal;

import java.util.UUID;

public class Account {
    private UUID id;
    private Double balance;

    public Account(UUID id, Double balance) {
        this.id = id;
        this.balance = balance;
    }


    public Double deposit(Double amount) {
        validateAmount(amount);
        balance = balance + amount;
        return balance;
    }

    public Double withdrawal(Double amount) {
        validateAmountForWithdrawal(amount);
        balance = balance - amount;
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    private void validateAmount(Double amount) {
        if (amount < 0) throw new RuntimeException("Invalid amount");
    }

    private void validateAmountForWithdrawal(Double amount) {
        validateAmount(amount);
        if (amount > balance) throw new RuntimeException("Insufficient balance");
    }
}
