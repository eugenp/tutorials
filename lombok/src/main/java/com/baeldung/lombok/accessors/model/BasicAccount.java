package com.baeldung.lombok.accessors.model;

import java.math.BigDecimal;

public class BasicAccount {
    private String name;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String accountName) {
        this.name = accountName;
    }
}
