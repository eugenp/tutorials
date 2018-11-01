package com.baeldung.hexagonal.adapters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.core.model.ExchangeRate;
import com.baeldung.hexagonal.core.ports.ExchangeRateRetrievalService;

@Service
public class DummyRetrievalService implements ExchangeRateRetrievalService {

    @Override
    public List<ExchangeRate> retrieve(LocalDate startDate, Currency src, Currency dest) {
        return SampleData.generate(startDate, src, dest);
    }

    private static class SampleData {

        static List<ExchangeRate> generate(LocalDate startDate, Currency src, Currency dest) {
            List<ExchangeRate> result = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                result.add(new ExchangeRate(startDate.plus(i, ChronoUnit.DAYS), src, dest, BigDecimal.valueOf(ThreadLocalRandom.current()
                    .nextDouble(10, 60))));
            }
            return result;
        }
    }
}
