package com.baeldung.concurrent.virtualthreads.vs.webflux.model;

import java.math.BigDecimal;

public record Price(BigDecimal value, String currency) {
    public Price applyDiscount(BigDecimal discount) {
        return new Price(value.subtract(discount), currency);
    }
}