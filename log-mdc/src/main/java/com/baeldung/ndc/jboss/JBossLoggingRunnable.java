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
        // Add transactionId and owner to NDC. They will remain throughout
        NDC.push("tx.id=" + tx.getTransactionId());
        NDC.push("tx.owner" + tx.getOwner());

        // Add the destination savingsAccountId to NDC
        NDC.push("Destination savingsAccountId=" + tx.getSavingsAccountId());

        boolean transferSuccess = jbossLoggingBusinessService.transfer(tx.getAmount(), tx.getSavingsAccountId());

        // take out savingsAccountId from NDC stack
        NDC.pop();

        if (transferSuccess && tx.isInvestmentFund()) {
            // Add the destination investmentFundId to NDC
            NDC.push("Destination investmentFundId=" + tx.getInvestmentFundId());

            jbossLoggingBusinessService.transfer(tx.getAmount(), tx.getInvestmentFundId());

            // take out investmentFundId from NDC
            NDC.pop();
        }
        NDC.clear();
    }
}