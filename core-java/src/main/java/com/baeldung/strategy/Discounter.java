package com.baeldung.strategy;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public interface Discounter extends UnaryOperator<BigDecimal> {

    static Discounter christmas() {
        return (amount) -> amount.multiply(BigDecimal.valueOf(0.9));
    }

    static Discounter newYear() {
        return (amount) -> amount.multiply(BigDecimal.valueOf(0.8));
    }

    static Discounter easter() {
        return (amount) -> amount.multiply(BigDecimal.valueOf(0.5));
    }
}
