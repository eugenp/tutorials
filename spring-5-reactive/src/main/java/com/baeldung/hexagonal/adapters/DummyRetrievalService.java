package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.model.ExchangeRate;
import com.baeldung.hexagonal.core.model.ExchangeRateDomain;
import com.baeldung.hexagonal.core.ports.ExchangeRateRetrievalService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DummyRetrievalService implements ExchangeRateRetrievalService {

    @Override
    public List<ExchangeRateDomain> retrieve(LocalDate startDate, Currency src, Currency dest) {
        return SampleData.generate(startDate, src, dest);
    }


    private static class SampleData {

        static List<ExchangeRateDomain> generate(LocalDate startDate, Currency src, Currency dest) {
            List<ExchangeRateDomain> result = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                result.add(new ExchangeRate(startDate.plus(i, ChronoUnit.DAYS), src, dest,
                        BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10, 60))));
            }
            return result;
        }
    }
}
