package com.baeldung.dddhexagonalsimple.domain;

import com.baeldung.dddhexagonalsimple.domain.model.Pizza;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceCalculatorUnitTest {

    PriceCalculator priceCalculator = new PriceCalculator();

    @DisplayName(
      "given a pepperoni pizza costs $0.45 per square inch, " +
      "and an order comes in for a 12-inch pepperoni pizza, " +
      "when the order is processed, " +
      "then the price of the order should be $16.96"
    )
    @Test
    void test() {
        Pizza pepperoni = Pizza.builder().name("pepperoni").pricePerSquareInch(BigDecimal.valueOf(0.15)).build();
        BigDecimal priceOfOrder = priceCalculator.calculateRoundedPriceOfPizza(pepperoni, 12);

        assertEquals(BigDecimal.valueOf(16.96), priceOfOrder);
    }
}
