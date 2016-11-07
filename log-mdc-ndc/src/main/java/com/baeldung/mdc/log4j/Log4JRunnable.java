package com.baeldung.mdc.log4j;

import org.apache.log4j.MDC;

import com.baeldung.mdc.Transaction;

public class Log4JRunnable implements Runnable {

    private Transaction tx;
    private static Log4JBusinessService log4jBusinessService = new Log4JBusinessService();

    public Log4JRunnable(Transaction tx) {
        this.tx = tx;
    }

    public void run() {

        MDC.put("transaction.id", tx.getTransactionId());
        MDC.put("transaction.owner", tx.getOwner());

        log4jBusinessService.transfer(tx.getAmount());

        MDC.clear();

    }
}