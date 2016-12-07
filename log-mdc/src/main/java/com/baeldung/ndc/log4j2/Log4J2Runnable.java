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
        // Add transactionId and owner to NDC. They will remain throughout
        ThreadContext.push("tx.id=" + tx.getTransactionId());
        ThreadContext.push("tx.owner=" + tx.getOwner());

        // Add the destination savingsAccountId to NDC
        ThreadContext.push("Destination savingsAccountId=" + tx.getSavingsAccountId());

        boolean transferSuccess = log4j2BusinessService.transfer(tx.getAmount(), tx.getSavingsAccountId());

        // take out savingsAccountId from NDC stack
        ThreadContext.pop();

        if (transferSuccess && tx.isInvestmentFund()) {
            // Add the destination investmentFundId to NDC
            ThreadContext.push("Destination investmentFundId=" + tx.getInvestmentFundId());

            log4j2BusinessService.transfer(tx.getAmount(), tx.getInvestmentFundId());

            // take out investmentFundId from NDC
            ThreadContext.pop();
        }
        ThreadContext.clearAll();
    }
}