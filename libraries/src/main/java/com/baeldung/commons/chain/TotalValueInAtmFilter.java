package com.baeldung.commons.chain;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;

import static com.baeldung.commons.chain.AtmConstants.*;

public class TotalValueInAtmFilter implements Filter {

    @Override
    public boolean postprocess(Context context, Exception exception) {
        context.put(TOTAL_IN_ATM, (int) context.get(TOTAL_IN_ATM) - calculateAmountDeducted(context));
        return false;
    }

    private int calculateAmountDeducted(Context context) {
        return (int) context.get(HUNDRED) * 100 + (int) context.get(FIFTY) * 50 + (int) context.get(TEN) * 10;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        return false;
    }
}