package com.baeldung.strategy;

import java.math.BigDecimal;

public class EasterDiscounter implements Discounter {
    @Override
    public BigDecimal applyDiscount(final BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.5));
    }
}
