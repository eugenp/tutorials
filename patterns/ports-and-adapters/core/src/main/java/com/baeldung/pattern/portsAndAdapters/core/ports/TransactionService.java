package com.baeldung.pattern.portsAndAdapters.core.ports;

import com.baeldung.pattern.portsAndAdapters.core.model.Transaction;

public interface TransactionService {
    int add(Transaction transaction);
}
