package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.core.model.ExchangeRateDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public interface ExchangeRateService {

    UpdateResult update(Currency src, Currency dest);

    List<ExchangeRateDomain> getHistory(LocalDate from, LocalDate to, Currency src, Currency dest);

    @Getter
    @AllArgsConstructor
    class UpdateResult {
        final Status status;
        final String message;

        public enum Status {
            OK, ERROR,
        }
    }
}
