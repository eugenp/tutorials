package com.baeldung.commons.chain;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.junit.Assert;
import org.junit.Test;

import static com.baeldung.commons.chain.AtmConstants.*;

public class AtmChainUnitTest {

    public static final int EXPECTED_TOTAL_AMOUNT_TO_BE_WITHDRAWN = 460;
    public static final int EXPECTED_AMOUNT_LEFT_TO_BE_WITHDRAWN = 0;
    public static final int EXPECTED_NO_OF_HUNDREDS_DISPENSED = 4;
    public static final int EXPECTED_NO_OF_FIFTIES_DISPENSED = 1;
    public static final int EXPECTED_NO_OF_TENS_DISPENSED = 1;

    @Test
    public void givenInputsToContext_whenAppliedChain_thenExpectedContext() {
        Context context = new AtmRequestContext();
        context.put(TOTAL_AMOUNT_TO_BE_WITHDRAWN, 460);
        context.put(AMOUNT_LEFT_TO_BE_WITHDRAWN, 460);
        Catalog catalog = new AtmCatalog();
        Command atmWithdrawalChain = catalog.getCommand(ATM_WITHDRAWAL_CHAIN);
        try {
            atmWithdrawalChain.execute(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(EXPECTED_TOTAL_AMOUNT_TO_BE_WITHDRAWN, (int) context.get(TOTAL_AMOUNT_TO_BE_WITHDRAWN));
        Assert.assertEquals(EXPECTED_AMOUNT_LEFT_TO_BE_WITHDRAWN, (int) context.get(AMOUNT_LEFT_TO_BE_WITHDRAWN));
        Assert.assertEquals(EXPECTED_NO_OF_HUNDREDS_DISPENSED, (int) context.get(NO_OF_HUNDREDS_DISPENSED));
        Assert.assertEquals(EXPECTED_NO_OF_FIFTIES_DISPENSED, (int) context.get(NO_OF_FIFTIES_DISPENSED));
        Assert.assertEquals(EXPECTED_NO_OF_TENS_DISPENSED, (int) context.get(NO_OF_TENS_DISPENSED));
    }
}