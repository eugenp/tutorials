package com.baeldung.ndc.jboss;

import org.jboss.logging.NDC;

import com.baeldung.ndc.Investment;

public class JBossLoggingRunnable implements Runnable {
    private final Investment tx;
    private JBossLoggingInvestmentService jbossLoggingBusinessService = new JBossLoggingInvestmentService();

    public JBossLoggingRunnable(Investment tx) {
        this.tx = tx;
    }

    public void run() {
        // Add transactionId and owner to NDC
        NDC.push("tx.id=" + tx.getTransactionId());
        NDC.push("tx.owner" + tx.getOwner());

        try {
            jbossLoggingBusinessService.transfer(tx.getAmount());
        } finally {
            // take out owner from the NDC stack
            NDC.pop();

            // take out transactionId from the NDC stack
            NDC.pop();

            NDC.clear();
        }
    }
}