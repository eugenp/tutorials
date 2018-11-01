package com.baeldung.hexagonal.core.service;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import com.baeldung.hexagonal.core.model.ExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface ExchangeRateService {

    UpdateResult update(Currency src, Currency dest);

    List<ExchangeRate> getHistory(LocalDate from, LocalDate to, Currency src, Currency dest);

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
