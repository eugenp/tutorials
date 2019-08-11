package com.baeldung.boot.architecture.hexagonal.backend;

import com.baeldung.boot.architecture.hexagonal.entity.Transaction;

public interface IDBPort {
    int addTransaction(Transaction transaction);
    Transaction getTransaction(int transactionId);
}
