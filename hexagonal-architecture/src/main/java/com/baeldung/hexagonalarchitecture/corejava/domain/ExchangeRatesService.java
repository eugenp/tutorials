package com.baeldung.hexagonalarchitecture.corejava.domain;


import com.baeldung.hexagonalarchitecture.corejava.adapter.driven.MonthlyExchangeRateInfrastructureAdapter;
import com.baeldung.hexagonalarchitecture.corejava.port.driven.ExchangeRateInfrastructurePort;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExchangeRatesService {

    private ExchangeRateInfrastructurePort exchangeRateInfrastructurePort;

    public ExchangeRatesService() {
        this.exchangeRateInfrastructurePort = new MonthlyExchangeRateInfrastructureAdapter();
    }

    public List<String> loadAvailableCurrencies() {
        List<ExchangeRate> availableCurrencies = exchangeRateInfrastructurePort.loadExchangeRates();
        return availableCurrencies.stream().map(ExchangeRate::getCurrencyCode).collect(Collectors.toList());
    }

    public double getCurrencyExchangeRatesForDate(String currencyCode, Date date) {
        List<ExchangeRate> exchangeRates = exchangeRateInfrastructurePort.loadExchangeRates()
                .stream().filter(currency -> currency.getCurrencyCode().equals(currencyCode))
                .filter(currency -> currency.getDate().equals(date)).collect(Collectors.toList());

        if (exchangeRates.size() > 1) {
            // todo use proper exception
            throw new IllegalStateException("");
        }

        if (exchangeRates.size() == 0) {
            // todo use proper exception
            throw new IllegalStateException("");
        }

        return exchangeRates.get(0).getExchangeRate();
    }
}
