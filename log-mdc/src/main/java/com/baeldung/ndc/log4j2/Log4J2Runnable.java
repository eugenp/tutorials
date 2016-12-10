package com.baeldung.ndc.log4j2;

import org.apache.logging.log4j.ThreadContext;

import com.baeldung.ndc.Investment;

public class Log4J2Runnable implements Runnable {
    private final Investment tx;
    private Log4j2InvestmentService log4j2BusinessService = new Log4j2InvestmentService();

    public Log4J2Runnable(Investment tx) {
        this.tx = tx;
    }

    public void run() {
        // Add transactionId and owner to NDC
        ThreadContext.push("tx.id=" + tx.getTransactionId());
        ThreadContext.push("tx.owner=" + tx.getOwner());

        try {
            log4j2BusinessService.transfer(tx.getAmount());
        } finally {
            // take out owner from the NDC stack
            ThreadContext.pop();

            // take out transactionId from the NDC stack
            ThreadContext.pop();

            ThreadContext.clearAll();
        }
    }
}