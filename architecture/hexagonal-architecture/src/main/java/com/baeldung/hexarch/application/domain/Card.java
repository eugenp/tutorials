package com.baeldung.hexarch.application.domain;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
public class Card {
    @Id
    private Long id;
    private BigDecimal balance;
    private BigDecimal creditBalance;
    private Boolean isDebit;

    public boolean swipe(BigDecimal amount, boolean isCash) {
        if (isDebit) {
            return debitSwipe(amount);
        } else {
            return creditSwipe(amount, isCash);
        }
    }

    private boolean creditSwipe(BigDecimal amount, boolean isCash) {
        if (isCash) {
            return withdraw(amount);
        } else {
            return swipe(amount);
        }
    }

    private boolean debitSwipe(BigDecimal amount) {
        return withdraw(amount);
    }

    private boolean withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            return false;
        }
        balance = balance.subtract(amount);
        return true;
    }

    private boolean swipe(BigDecimal amount) {
        if (creditBalance.compareTo(amount) < 0) {
            return false;
        }
        balance = creditBalance.subtract(amount);
        return true;
    }

    public void pay(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
