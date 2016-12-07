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

        // Add transactionId and owner to NDC. They will remain throughout
        NDC.push("tx.id=" + tx.getTransactionId());
        NDC.push("tx.owner=" + tx.getOwner());

        // Add the destination savingsAccountId to NDC
        NDC.push("Destination savingsAccountId=" + tx.getSavingsAccountId());

        boolean transferSuccess = log4jBusinessService.transfer(tx.getAmount(), tx.getSavingsAccountId());

        // take out savingsAccountId from NDC stack
        NDC.pop();

        if (transferSuccess && tx.isInvestmentFund()) {
            // Add the destination investmentFundId to NDC
            NDC.push("Destination investmentFundId=" + tx.getInvestmentFundId());

            log4jBusinessService.transfer(tx.getAmount(), tx.getInvestmentFundId());

            // take out investmentFundId from NDC
            NDC.pop();
        }
        NDC.remove();
    }
}