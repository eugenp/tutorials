package com.baeldung.commons.chain;

import static com.baeldung.commons.chain.AtmConstants.TEN;

public class TenDenominationCommand extends AbstractDenominationCommand {
    @Override
    protected String getDenominationString() {
        return TEN;
    }

    @Override
    protected int getDenominationValue() {
        return 10;
    }
}