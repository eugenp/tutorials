package com.baeldung.commons.chain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import static com.baeldung.commons.chain.AtmConstants.REMAINING;
import static com.baeldung.commons.chain.AtmConstants.TOTAL_IN_ATM;

public abstract class AbstractDenominationCommand implements Command {

    @Override
    public boolean execute(Context context) throws Exception {
        int remaining = (int) context.get(REMAINING);
        if (remaining <= (int) context.get(TOTAL_IN_ATM) && remaining >= getDenominationValue()) {
            context.put(getDenominationString(), remaining / getDenominationValue());
            context.put(REMAINING, remaining % getDenominationValue());
        }
        return false;
    }

    protected abstract String getDenominationString();

    protected abstract int getDenominationValue();
}