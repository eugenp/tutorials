package com.baeldung.mdc.jboss;

import org.jboss.logging.MDC;
import org.jboss.logging.NDC;

import com.baeldung.mdc.Transfer;

public class JBossLoggingRunnable implements Runnable {
    private final Transfer tx;
    private JBossLoggingTransferService jbossLoggingBusinessService = new JBossLoggingTransferService();

    public JBossLoggingRunnable(Transfer tx) {
        this.tx = tx;
    }

    public void run() {
        MDC.put("transaction.id", tx.getTransactionId());
        MDC.put("transaction.owner", tx.getSender());

        // Use NDC to add accountId
        NDC.push("accountId " + tx.getAccountId());

        boolean transferSuccess = jbossLoggingBusinessService.transfer(tx.getAmount(), tx.getAccountId());

        // Pop accountId
        NDC.pop();

        if (transferSuccess && tx.isInvestmentFund()) {
            // Use NDC to add investmentFundId
            NDC.push("investmentFundId " + tx.getInvestmentFundId());

            jbossLoggingBusinessService.transfer(tx.getAmount(), tx.getInvestmentFundId());

            // Pop investmentFundId
            NDC.pop();
        }

        NDC.clear();

        MDC.clear();
    }
}