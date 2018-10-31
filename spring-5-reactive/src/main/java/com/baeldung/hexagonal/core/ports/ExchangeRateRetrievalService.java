package com.baeldung.hexagonal.core.ports;

import com.baeldung.hexagonal.core.model.ExchangeRateDomain;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public interface ExchangeRateRetrievalService {

    List<ExchangeRateDomain> retrieve(LocalDate date, Currency src, Currency dest);
}
