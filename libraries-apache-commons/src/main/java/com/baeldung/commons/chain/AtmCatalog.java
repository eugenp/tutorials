package com.baeldung.commons.chain;

import org.apache.commons.chain.impl.CatalogBase;

import static com.baeldung.commons.chain.AtmConstants.ATM_WITHDRAWAL_CHAIN;

public class AtmCatalog extends CatalogBase {

    public AtmCatalog() {
        super();
        addCommand(ATM_WITHDRAWAL_CHAIN, new AtmWithdrawalChain());
    }
}