package com.baeldung.mdc.slf4j;

import org.slf4j.MDC;

import com.baeldung.mdc.Transfer;

public class Slf4jRunnable implements Runnable {
    private final Transfer tx;
    private static Slf4TransferService slf4TransferService = new Slf4TransferService();

    public Slf4jRunnable(Transfer tx) {
        this.tx = tx;
    }

    public void run() {

        MDC.put("transaction.id", tx.getTransactionId());
        MDC.put("transaction.owner", tx.getSender());

        boolean transferSuccess = slf4TransferService.transfer(tx.getAmount(), tx.getAccountId());

        if (transferSuccess && tx.isInvestmentFund()) {
            slf4TransferService.transfer(tx.getAmount(), tx.getInvestmentFundId());
        }
        MDC.clear();

    }
}