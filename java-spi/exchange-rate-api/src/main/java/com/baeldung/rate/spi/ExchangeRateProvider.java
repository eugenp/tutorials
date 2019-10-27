package com.baeldung.rate.spi;

import com.baeldung.rate.api.QuoteManager;

public interface ExchangeRateProvider {
    QuoteManager create();
}