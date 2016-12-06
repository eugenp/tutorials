package com.baeldung.mdc.log4j2;

import org.apache.logging.log4j.ThreadContext;

import com.baeldung.mdc.Transfer;

public class Log4J2Runnable implements Runnable {
    private final Transfer tx;
    private Log4J2TransferService log4j2BusinessService = new Log4J2TransferService();

    public Log4J2Runnable(Transfer tx) {
        this.tx = tx;
    }

    public void run() {

        ThreadContext.put("transaction.id", tx.getTransactionId());
        ThreadContext.put("transaction.owner", tx.getSender());

        // Use NDC to add accountId
        ThreadContext.push("accountId " + tx.getAccountId());

        boolean transferSuccess = log4j2BusinessService.transfer(tx.getAmount(), tx.getAccountId());

        // Pop accountId
        ThreadContext.pop();

        if (transferSuccess && tx.isInvestmentFund()) {
            // Use NDC to add investmentFundId
            ThreadContext.push("investmentFundId " + tx.getInvestmentFundId());

            log4j2BusinessService.transfer(tx.getAmount(), tx.getInvestmentFundId());

            // Pop investmentFundId
            ThreadContext.pop();
        }

        ThreadContext.clearAll();

    }
}