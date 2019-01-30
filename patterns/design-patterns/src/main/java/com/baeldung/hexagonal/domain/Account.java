package com.baeldung.hexagonal.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {

    private String accountNumber;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void applyTransaction(Transaction transaction) {
        balance = balance.add(transaction.getAmount());
        transactions.add(transaction);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, transactions);
    }

}
