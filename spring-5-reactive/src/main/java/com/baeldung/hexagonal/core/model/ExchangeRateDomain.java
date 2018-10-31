package com.baeldung.hexagonal.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public interface ExchangeRateDomain {
    LocalDate getDate();

    Currency getFrom();

    Currency getTo();

    BigDecimal getRate();
}
