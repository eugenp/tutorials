package com.baeldung.hexagonalarchitecture.corejava.adapter.driving;


import com.baeldung.hexagonalarchitecture.corejava.domain.ExchangeRatesService;
import com.baeldung.hexagonalarchitecture.corejava.port.driving.ExchangeRatesApplicationPort;

import java.util.Date;
import java.util.Set;

//  this specific driving port implementation is just calling service methods in this case
public class ExchangeRatesApplicationAdapter implements ExchangeRatesApplicationPort {

    private ExchangeRatesService exchangeRatesService;

    public ExchangeRatesApplicationAdapter() {
        this.exchangeRatesService = new ExchangeRatesService();
    }

    @Override
    public Set<String> loadAvailableCurrencies() {
        return exchangeRatesService.loadAvailableCurrencies();
    }

    @Override
    public double getCurrencyExchangeRatesForDate(String currencyCode, Date date) {
        return exchangeRatesService.getCurrencyExchangeRatesForDate(currencyCode, date);
    }
}
