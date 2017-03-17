package com.baeldung.noinjection.consumer;

import java.math.BigDecimal;

public class AccountTransferImpl implements IAccountTransfer {
    private IAccountTransaction transaction;

    public void setTransaction(IAccountTransaction transaction) {
        this.transaction = transaction;
    }

    public void deposit(BigDecimal amount) {
        transaction.validateTransaction(amount);

    }

    public void withdraw(BigDecimal amount) {
        transaction.validateTransaction(amount);

    }
}
