package com.baeldung.noinjection.consumer;

import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class AccountTransferImpl implements AccountTransfer {
    private AccountTransaction transaction;

    public void setTransaction(AccountTransaction transaction) {
        this.transaction = transaction;
    }

    public void deposit(BigDecimal amount) {
        transaction.validateTransaction(amount);

    }

    public void withdraw(BigDecimal amount) {
        transaction.validateTransaction(amount);

    }
}
