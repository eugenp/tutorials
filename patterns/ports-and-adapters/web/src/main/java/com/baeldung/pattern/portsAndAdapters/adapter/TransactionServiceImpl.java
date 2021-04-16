package com.baeldung.pattern.portsAndAdapters.adapter;

import com.baeldung.pattern.portsAndAdapters.core.model.Transaction;
import com.baeldung.pattern.portsAndAdapters.core.ports.TransactionService;
import com.baeldung.pattern.portsAndAdapters.core.service.TransactionProcessor;

public class TransactionServiceImpl implements TransactionService {

    final TransactionProcessor processor;

    public TransactionServiceImpl(TransactionProcessor processor) {
        this.processor = processor;
    }

    @Override
    public int add(Transaction transaction) {
        return processor.processTransaction(transaction);
    }

}
