package com.baeldung.dddintroduction.discountcalculator.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@NoArgsConstructor
public class DiscountCalculatorService {

    public BigDecimal calculateDiscount(final BigDecimal price, final BigDecimal discount) {
        final BigDecimal discountAmount = price.multiply(discount).divide(new BigDecimal(100L));
        return price.subtract(discountAmount);
    }
}
