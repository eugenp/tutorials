package com.baeldung.hexagonal.adapters;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.core.model.ExchangeRate;
import com.baeldung.hexagonal.core.ports.ExchangeRateRepository;

@Repository
public class ExchangeRateInMemoryRepository implements ExchangeRateRepository {

    private final Map<LocalDate, ExchangeRate> store = new TreeMap<>();

    @Override
    public void save(List<ExchangeRate> exchangeRates) {
        store.putAll(exchangeRates.stream()
            .collect(Collectors.toMap(ExchangeRate::getDate, Function.identity())));
    }

    @Override
    public List<ExchangeRate> retrieve(LocalDate from, LocalDate to, Currency src, Currency dest) {
        List<ExchangeRate> result = new ArrayList<>();
        for (LocalDate i = from; i.isBefore(to); i = i.plus(1, ChronoUnit.DAYS)) {
            ExchangeRate item = store.get(i);
            if (item != null && item.getFrom()
                .equals(src) && item.getTo()
                    .equals(dest)) {
                result.add(item);
            }
        }
        return result;
    }
}
