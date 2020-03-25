package com.baeldung.hexagonalarchitecture.corejava.adapter.driven;


import com.baeldung.hexagonalarchitecture.corejava.domain.ExchangeRate;
import com.baeldung.hexagonalarchitecture.corejava.parser.ExchangeRateDateParser;
import com.baeldung.hexagonalarchitecture.corejava.port.driven.ExchangeRateInfrastructurePort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class MonthlyExchangeRateInfrastructureAdapter implements ExchangeRateInfrastructurePort {

    private static final String EXCHANGE_RATES_SOURCE_FILE = "exchange-rates.csv";

    private ExchangeRateDateParser parser;

    public MonthlyExchangeRateInfrastructureAdapter() {
        this.parser = new ExchangeRateDateParser();
    }

    @Override
    public List<ExchangeRate> loadExchangeRates() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(EXCHANGE_RATES_SOURCE_FILE)))) {
            // skip the header of the csv
            return reader.lines().skip(1).map(line -> {
                String[] exchangeRate = line.split(",");
                return new ExchangeRate(exchangeRate[1], parser.parseExchangeRateDate(exchangeRate[0]), Double.parseDouble(exchangeRate[2]));
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Loading from exchange rates file has failed.");
        }
    }
}
