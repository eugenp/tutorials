package com.baeldung.lombok.configexamples;

import lombok.*;
import lombok.extern.java.Log;

import java.util.logging.Level;

import static java.lang.Math.abs;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Log
public class Account {

    @NonNull
    private Double balance = 0.;
    @NonNull
    private String accountHolder = "";

    public Account addBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Can not add negative amount");
        }

        this.balance += amount;
        return this;
    }

    public Account withdraw(double amount) {
        if (this.balance - abs(amount) < 0) {
            domainLog.log(Level.INFO, String.format("Transaction denied for account holder: %s", this.accountHolder));
            throw new IllegalArgumentException(String.format("Not enough balance, you have %.2f", this.balance));
        }

        this.balance -= abs(amount);
        return this;
    }
}