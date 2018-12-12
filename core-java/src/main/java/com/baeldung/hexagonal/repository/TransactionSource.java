package com.baeldung.hexagonal.repository;

import java.util.List;

import com.baeldung.hexagonal.domain.Transaction;

public interface TransactionSource {

    List<Transaction> getTransactions();
}
