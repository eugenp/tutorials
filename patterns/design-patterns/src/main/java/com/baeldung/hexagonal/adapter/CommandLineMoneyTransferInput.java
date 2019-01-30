package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.application.MoneyTransferInput;
import com.baeldung.hexagonal.domain.Transaction;

import java.math.BigDecimal;

public class CommandLineMoneyTransferInput implements MoneyTransferInput {

    private final String accountNumber;
    private final Transaction transaction;

    public CommandLineMoneyTransferInput(String accountNumber, String amount) {
        this.accountNumber = accountNumber;
        this.transaction = new Transaction(new BigDecimal(amount));
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }
}
