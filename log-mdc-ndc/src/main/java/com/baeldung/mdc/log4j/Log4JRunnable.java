package com.baeldung.mdc.log4j;

import org.apache.log4j.MDC;
import org.apache.logging.log4j.ThreadContext;

import com.baeldung.mdc.Transaction;
import com.baeldung.mdc.log4j2.Log4J2BusinessService;

public class Log4JRunnable implements Runnable {

    private Transaction tx;
    private static Log4JBusinessService log4jBusinessService = new Log4JBusinessService();

    public Log4JRunnable(Transaction tx) {
        this.tx = tx;
    }

    public void run() {

        MDC.put("transaction.id", tx.getTransactionId());
        MDC.put("transaction.owner", tx.getOwner());
        MDC.put("transaction.createdAt", tx.getCreatedAt());

        log4jBusinessService.transfer(tx.getAmount());

        MDC.clear();

    }
}