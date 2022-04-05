package com.baeldung.rate.impl;

import com.baeldung.rate.api.QuoteManager;
import com.baeldung.rate.spi.ExchangeRateProvider;

public class YahooFinanceExchangeRateProvider implements ExchangeRateProvider {

    @Override
    public QuoteManager create() {
        return new YahooQuoteManagerImpl();
    }

}