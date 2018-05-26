package com.baeldung.springwebflux.repository;

import com.baeldung.springwebflux.model.Equity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class DataRepository {

    private static final Random random = new Random();

    private DataRepository() {
    }

    public static final Equity generateEquityPriceChanges() {
        int equityId = random.nextInt(101);
        double equityPrice = ThreadLocalRandom.current()
            .nextDouble(250.0);
        return Equity.builder()
            .equityId(equityId)
            .equityPrice(equityPrice)
            .build();
    }
}
