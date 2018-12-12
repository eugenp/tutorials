package com.baeldung.hexagonal.repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonal.domain.Transaction;

public class FixedTransactionSource implements TransactionSource {

    public List<Transaction> getTransactions() {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(ZonedDateTime.now(), 3, "Coffee Corner", "David", "EUR", "Latte"));
        transactions.add(new Transaction(ZonedDateTime.now(), 45000, "Coffee Corner", "David", "RUB", "Cappuccino"));
        transactions.add(new Transaction(ZonedDateTime.now(), 2, "Supermarket", "David", "EUR", "Coffee beans"));
        transactions.add(new Transaction(ZonedDateTime.now(), 10, "Bar", "David", "EUR", "Iced coffee"));
        transactions.add(new Transaction(ZonedDateTime.now(), 200, "Supermarket", "David", "EUR", "Groceries"));

        return transactions;
    }
}
