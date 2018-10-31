package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.model.ExchangeRateDomain;
import com.baeldung.hexagonal.core.ports.ExchangeRateRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ExchangeRateInMemoryRepository implements ExchangeRateRepository<ExchangeRateDomain> {

    private final Map<LocalDate, ExchangeRateDomain> store = new TreeMap<>();

    @Override
    public void save(List<ExchangeRateDomain> exchangeRates) {
        store.putAll(exchangeRates.stream()
            .collect(Collectors.toMap(ExchangeRateDomain::getDate, Function.identity())));
    }

    @Override
    public List<ExchangeRateDomain> retrieve(LocalDate from, LocalDate to, Currency src, Currency dest) {
        List<ExchangeRateDomain> result = new ArrayList<>();
        for (LocalDate i = from; i.isBefore(to); i = i.plus(1, ChronoUnit.DAYS)) {
            ExchangeRateDomain item = store.get(i);
            if (item != null && item.getFrom()
                .equals(src) && item.getTo()
                    .equals(dest)) {
                result.add(item);
            }
        }
        return result;
    }
}
