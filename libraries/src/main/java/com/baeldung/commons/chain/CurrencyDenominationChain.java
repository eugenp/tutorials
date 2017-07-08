package com.baeldung.commons.chain;

import org.apache.commons.chain.impl.ChainBase;

public class CurrencyDenominationChain extends ChainBase {

    public CurrencyDenominationChain() {
        super();
        addCommand(new HundredDenominationCommand());
        addCommand(new FiftyDenominationCommand());
        addCommand(new TenDenominationCommand());
        addCommand(new TotalValueInAtmFilter());
    }
}