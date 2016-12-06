package com.baeldung.mdc;

import static java.lang.Math.floor;
import static java.lang.Math.random;

public class TransactionFactory {

    private static final String[] NAMES = { "John", "Susan", "Marc", "Samantha" };
    private static long nextId = 1;

    public Transfer newInstance() {
        String transactionId = String.valueOf(nextId++);
        String owner = NAMES[(int) floor(random() * NAMES.length)];
        String accountId = Long.toString((long) (random() * 900000000 + 100000000));
        long amount = (long) (random() * 1500 + 500);

        // indicate if this is an investment fund
        boolean isInvestmentFund = Math.random() < 0.50;
        String investmentFundId = Long.toString((long) (random() * 900000 + 100000));
        Transfer tx = new Transfer(transactionId, owner, amount, accountId, isInvestmentFund, investmentFundId);
        return tx;
    }

}
