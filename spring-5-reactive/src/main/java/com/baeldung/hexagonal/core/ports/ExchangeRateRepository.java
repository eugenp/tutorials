package com.baeldung.hexagonal.core.ports;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import com.baeldung.hexagonal.core.model.ExchangeRate;

public interface ExchangeRateRepository {

    void save(List<ExchangeRate> exchangeRates);

    List<ExchangeRate> retrieve(LocalDate from, LocalDate to, Currency src, Currency dest);

}
