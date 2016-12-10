package com.baeldung.ndc;

import static java.lang.Math.floor;
import static java.lang.Math.random;

public class InvestmentTransactionFactory {
    private static final String[] NAMES = { "John", "Susan", "Marc", "Samantha" };
    private static long nextId = 1;

    public Investment newInstance() {
        String transactionId = String.valueOf(nextId++);
        String owner = NAMES[(int) floor(random() * NAMES.length)];
        long amount = (long) (random() * 1500 + 500);

        Investment tx = new Investment(transactionId, owner, amount);

        return tx;
    }
}
