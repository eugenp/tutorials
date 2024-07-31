package com.baeldung.l.advanced;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    @Override
    protected void deposit(BigDecimal amount) {
        // Deposit into CurrentAccount
    }

    @Override
    protected void withdraw(BigDecimal amount) {
        // Withdraw from CurrentAccount
    }
}
