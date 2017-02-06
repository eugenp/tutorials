package com.baeldung.strategy;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

public class StrategyDesignPatternUnitTest {
    @Test
    public void shouldDivideByTwo_WhenApplyingStaffDiscounter() {
        Discounter staffDiscounter = new EasterDiscounter();

        final BigDecimal discountedValue = staffDiscounter
          .applyDiscount(BigDecimal.valueOf(100));

        assertThat(discountedValue)
          .isEqualByComparingTo(BigDecimal.valueOf(50));
    }

    @Test
    public void shouldDivideByTwo_WhenApplyingStaffDiscounterWithAnonyousTypes() {
        Discounter staffDiscounter = new Discounter() {
            @Override
            public BigDecimal applyDiscount(final BigDecimal amount) {
                return amount.multiply(BigDecimal.valueOf(0.5));
            }
        };

        final BigDecimal discountedValue = staffDiscounter
          .applyDiscount(BigDecimal.valueOf(100));

        assertThat(discountedValue)
          .isEqualByComparingTo(BigDecimal.valueOf(50));
    }

    @Test
    public void shouldDivideByTwo_WhenApplyingStaffDiscounterWithLamda() {
        Discounter staffDiscounter = amount -> amount.multiply(BigDecimal.valueOf(0.5));

        final BigDecimal discountedValue = staffDiscounter
          .applyDiscount(BigDecimal.valueOf(100));

        assertThat(discountedValue)
          .isEqualByComparingTo(BigDecimal.valueOf(50));
    }

    @Test
    public void shouldApplyListOfDiscounts() {
        List<Discounter> discounters = newArrayList();

        BigDecimal amount = BigDecimal.valueOf(100);

        discounters.forEach((d) -> d.applyDiscount(amount));
    }
}
