package com.baeldung.commons.chain;

import static com.baeldung.commons.chain.AtmConstants.NO_OF_TENS_DISPENSED;

public class TenDenominationDispenser extends AbstractDenominationDispenser {
    @Override
    protected String getDenominationString() {
        return NO_OF_TENS_DISPENSED;
    }

    @Override
    protected int getDenominationValue() {
        return 10;
    }
}