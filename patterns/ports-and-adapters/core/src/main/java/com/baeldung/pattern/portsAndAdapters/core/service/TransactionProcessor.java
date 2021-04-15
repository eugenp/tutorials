package com.baeldung.pattern.portsAndAdapters.core.service;

import com.baeldung.pattern.portsAndAdapters.core.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);

    public void processTransaction(Transaction transaction){
       logger.info("Transaction logged: {} description, {} amount", transaction.getDescription(), transaction.getAmount());
    }
}
