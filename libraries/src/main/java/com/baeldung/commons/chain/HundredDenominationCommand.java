package com.baeldung.commons.chain;

import static com.baeldung.commons.chain.AtmConstants.HUNDRED;

public class HundredDenominationCommand extends AbstractDenominationCommand {
    @Override
    protected String getDenominationString() {
        return HUNDRED;
    }

    @Override
    protected int getDenominationValue() {
        return 100;
    }
}