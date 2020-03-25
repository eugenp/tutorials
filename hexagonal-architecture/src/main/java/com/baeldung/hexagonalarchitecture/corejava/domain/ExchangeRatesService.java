package com.baeldung.hexagonalarchitecture.corejava.domain;

import com.baeldung.hexagonalarchitecture.corejava.adapter.driven.MonthlyExchangeRateInfrastructureAdapter;
import com.baeldung.hexagonalarchitecture.corejava.port.driven.ExchangeRateInfrastructurePort;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ExchangeRatesService {

    private ExchangeRateInfrastructurePort exchangeRateInfrastructurePort;

    public ExchangeRatesService() {
        this.exchangeRateInfrastructurePort = new MonthlyExchangeRateInfrastructureAdapter();
    }

    public Set<String> loadAvailableCurrencies() {
        List<ExchangeRate> availableCurrencies = exchangeRateInfrastructurePort.loadExchangeRates();
        return availableCurrencies.stream().map(ExchangeRate::getCurrencyCode).collect(Collectors.toSet());
    }

    public double getCurrencyExchangeRatesForDate(String currencyCode, Date date) {
        List<ExchangeRate> exchangeRates = exchangeRateInfrastructurePort.loadExchangeRates()
                .stream().filter(currency -> currency.getCurrencyCode().equals(currencyCode))
                .filter(currency -> currency.getDate().equals(date)).collect(Collectors.toList());

        if (exchangeRates.size() > 1) {
            throw new IllegalStateException(
                    String.format("More than one exchange rate was returned for %s currency and %s date", currencyCode, date.toString()));
        }

        if (exchangeRates.size() == 0) {
            throw new NoSuchElementException(
                    String.format("Exchange rate for %s currency and %s date was not found.", currencyCode, date.toString()));
        }

        return exchangeRates.get(0).getRate();
    }
}
