package com.baeldung.hexagonal.domain;

import java.math.BigDecimal;

public class BankAccount {

    private Long id;
    private BigDecimal balance;
    
    public BankAccount(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }
    
    public Long getId() {
        return id;
    }
    
    public boolean withdraw(BigDecimal amount) {
        if (!hasEnoughBalance(amount)) {
            return false;
        }
        balance = balance.subtract(amount);
        return true;
    }
    
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }
    
    private boolean hasEnoughBalance(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }
}