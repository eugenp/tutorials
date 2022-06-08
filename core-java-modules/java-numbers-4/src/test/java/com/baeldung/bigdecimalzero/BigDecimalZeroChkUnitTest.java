package com.baeldung.bigdecimalzero;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BigDecimalZeroChkUnitTest {
    private static final BigDecimal BD1 = new BigDecimal("0");
    private static final BigDecimal BD2 = new BigDecimal("0.0000");

    @Test
    void givenBD_whenCheckedWithEquals_shouldCheckedAsZero() {
        assertThat(BigDecimal.ZERO.equals(BD1)).isTrue();

        // in the article, we show the failure of the assertion below
        // assertThat(BigDecimal.ZERO.equals(BD2)).isTrue();

        assertThat(BigDecimal.ZERO.equals(BD2)).isFalse();
    }

    @Test
    void givenBD_whenCheckedWithCompareTo_shouldCheckedAsZero() {
        assertThat(BigDecimal.ZERO.compareTo(BD1)).isSameAs(0);
        assertThat(BigDecimal.ZERO.compareTo(BD2)).isSameAs(0);
    }

    @Test
    void givenBD_whenCheckedWithSignum_shouldCheckedAsZero() {
        assertThat(BD1.signum()).isSameAs(0);
        assertThat(BD2.signum()).isSameAs(0);
    }
}
