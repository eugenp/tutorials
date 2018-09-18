package com.baeldung.definition.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private int number;
    private BigDecimal balance;

    public Account(int number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }
}
