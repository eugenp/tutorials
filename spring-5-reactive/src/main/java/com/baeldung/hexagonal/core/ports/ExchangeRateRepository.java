package com.baeldung.hexagonal.core.ports;

import com.baeldung.hexagonal.core.model.ExchangeRateDomain;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public interface ExchangeRateRepository<T extends ExchangeRateDomain> {

    void save(List<T> exchangeRates);

    List<T> retrieve(LocalDate from, LocalDate to, Currency src, Currency dest);

}
