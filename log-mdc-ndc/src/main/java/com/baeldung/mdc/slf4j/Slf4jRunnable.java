package com.baeldung.mdc.slf4j;

import org.slf4j.MDC;

import com.baeldung.mdc.Transaction;

public class Slf4jRunnable implements Runnable {
    private final Transaction tx;

    public Slf4jRunnable(Transaction tx) {
        this.tx = tx;
    }

    public void run() {

        MDC.put("transaction.id", tx.getTransactionId());
        MDC.put("transaction.owner", tx.getOwner());

        new Slf4jBusinessService().transfer(tx.getAmount());

        MDC.clear();

    }
}