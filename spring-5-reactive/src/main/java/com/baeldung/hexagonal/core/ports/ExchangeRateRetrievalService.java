package com.baeldung.hexagonal.core.ports;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import com.baeldung.hexagonal.core.model.ExchangeRate;

public interface ExchangeRateRetrievalService {

    List<ExchangeRate> retrieve(LocalDate date, Currency src, Currency dest);
}
