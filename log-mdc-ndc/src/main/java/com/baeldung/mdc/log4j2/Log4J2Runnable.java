package com.baeldung.mdc.log4j2;

import org.apache.logging.log4j.ThreadContext;

import com.baeldung.mdc.Transaction;

public class Log4J2Runnable implements Runnable {
    private final Transaction tx;
    private Log4J2BusinessService log4j2BusinessService = new Log4J2BusinessService();

    public Log4J2Runnable(Transaction tx) {
        this.tx = tx;
    }

    public void run() {

        ThreadContext.put("transaction.id", tx.getTransactionId());
        ThreadContext.put("transaction.id", tx.getTransactionId());
        ThreadContext.put("transaction.owner", tx.getOwner());
        ThreadContext.put("transaction.createdAt", tx.getCreatedAt().toString());

        log4j2BusinessService.transfer(tx.getAmount());

        ThreadContext.clearAll();

    }
}