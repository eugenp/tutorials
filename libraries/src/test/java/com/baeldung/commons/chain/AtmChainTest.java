package com.baeldung.commons.chain;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.junit.Assert;
import org.junit.Test;

import static com.baeldung.commons.chain.AtmConstants.*;

public class AtmChainTest {

    public static final int EXPECTED_TOTAL_IN_ATM = 4040;
    public static final int EXPECTED_REMAINING = 0;
    public static final int EXPECTED_HUNDRED = 4;
    public static final int EXPECTED_FIFTY = 1;
    public static final int EXPECTED_TEN = 1;

    @Test
    public void givenInputsToContext_whenAppliedChain_thenExpectedContext() {
        Context context = new AtmRequestContext();
        context.put(TOTAL_IN_ATM, 4500);
        context.put(REMAINING, 460);
        Catalog catalog = new AtmCatalog();
        Command currencyDenominationChain = catalog.getCommand(CURRENCY_DENOMINATION_CHAIN);
        try {
            currencyDenominationChain.execute(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(EXPECTED_TOTAL_IN_ATM, (int) context.get(TOTAL_IN_ATM));
        Assert.assertEquals(EXPECTED_REMAINING, (int) context.get(REMAINING));
        Assert.assertEquals(EXPECTED_HUNDRED, (int) context.get(HUNDRED));
        Assert.assertEquals(EXPECTED_FIFTY, (int) context.get(FIFTY));
        Assert.assertEquals(EXPECTED_TEN, (int) context.get(TEN));
    }
}