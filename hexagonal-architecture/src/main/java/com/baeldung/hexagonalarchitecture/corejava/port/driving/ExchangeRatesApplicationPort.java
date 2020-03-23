package com.baeldung.hexagonalarchitecture.corejava.port.driving;

import java.util.Date;
import java.util.List;

public interface ExchangeRatesApplicationPort {
    List<String> loadAvailableCurrencies();
    double getCurrencyExchangeRatesForDate(String currencyCode, Date date);
}
