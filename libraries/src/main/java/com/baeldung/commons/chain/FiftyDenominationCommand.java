package com.baeldung.commons.chain;

import static com.baeldung.commons.chain.AtmConstants.FIFTY;

public class FiftyDenominationCommand extends AbstractDenominationCommand {
    @Override
    protected String getDenominationString() {
        return FIFTY;
    }

    @Override
    protected int getDenominationValue() {
        return 50;
    }
}