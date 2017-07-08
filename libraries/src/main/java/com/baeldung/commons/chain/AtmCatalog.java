package com.baeldung.commons.chain;

import org.apache.commons.chain.impl.CatalogBase;

import static com.baeldung.commons.chain.AtmConstants.CURRENCY_DENOMINATION_CHAIN;

public class AtmCatalog extends CatalogBase {

    public AtmCatalog() {
        super();
        addCommand(CURRENCY_DENOMINATION_CHAIN, new CurrencyDenominationChain());
    }
}