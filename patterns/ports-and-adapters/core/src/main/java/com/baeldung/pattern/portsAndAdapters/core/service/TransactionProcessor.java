package com.baeldung.pattern.portsAndAdapters.core.service;

import com.baeldung.pattern.portsAndAdapters.core.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TransactionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);

    private final List<Transaction> mockStorage = new ArrayList<>();

    public int processTransaction(Transaction transaction) {
        logger.info("Transaction logged: {} description, {} amount", transaction.getDescription(), transaction.getAmount());
        if (!mockStorage.contains(transaction))
            mockStorage.add(transaction);
        return mockStorage.indexOf(transaction);
    }
}
