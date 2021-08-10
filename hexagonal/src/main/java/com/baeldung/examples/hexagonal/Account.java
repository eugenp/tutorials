package main.java.com.baeldung.examples.hexagonal;

import java.util.UUID;

public class Account {
    private UUID id;
    private Double balance;

    public Account(UUID id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Double deposit(Double amount) {
        balance = balance + amount;
        return balance;
    }

    public Double withdrawal(Double amount) {
        balance = balance - amount;
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }
}
