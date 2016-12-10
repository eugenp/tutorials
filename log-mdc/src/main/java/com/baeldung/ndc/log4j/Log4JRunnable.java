package com.baeldung.ndc.log4j;

import org.apache.log4j.NDC;

import com.baeldung.ndc.Investment;

public class Log4JRunnable implements Runnable {
    private Investment tx;
    private static Log4jInvestmentService log4jBusinessService = new Log4jInvestmentService();

    public Log4JRunnable(Investment tx) {
        this.tx = tx;
    }

    public void run() {
        // Add transactionId and owner to NDC
        NDC.push("tx.id=" + tx.getTransactionId());
        NDC.push("tx.owner=" + tx.getOwner());

        try {
            log4jBusinessService.transfer(tx.getAmount());
        } finally {
            // take out owner from the NDC stack
            NDC.pop();

            // take out transactionId from the NDC stack
            NDC.pop();

            NDC.remove();
        }
    }
}