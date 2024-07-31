package com.baeldung.commons.chain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import static com.baeldung.commons.chain.AtmConstants.AMOUNT_LEFT_TO_BE_WITHDRAWN;

public abstract class AbstractDenominationDispenser implements Command {

    @Override
    public boolean execute(Context context) throws Exception {
        int amountLeftToBeWithdrawn = (int) context.get(AMOUNT_LEFT_TO_BE_WITHDRAWN);
        if (amountLeftToBeWithdrawn >= getDenominationValue()) {
            context.put(getDenominationString(), amountLeftToBeWithdrawn / getDenominationValue());
            context.put(AMOUNT_LEFT_TO_BE_WITHDRAWN, amountLeftToBeWithdrawn % getDenominationValue());
        }
        return false;
    }

    protected abstract String getDenominationString();

    protected abstract int getDenominationValue();
}