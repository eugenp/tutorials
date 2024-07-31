package com.baeldung.bigdecimaltointeger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

class BigDecimalToIntegerConversionUnitTest {

    @ParameterizedTest
    @ArgumentsSource(SmallBigDecimalConversionArgumentsProvider.class)
    void givenSmallBigDecimalWhenConvertToIntegerThenWontLosePrecision(BigDecimal given, int expected) {
        int actual = given.intValue();
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MAX_VALUE, Long.MAX_VALUE - 1, Long.MAX_VALUE - 2,
      Long.MAX_VALUE - 3, Long.MAX_VALUE - 4, Long.MAX_VALUE - 5,
      Long.MAX_VALUE - 6, Long.MAX_VALUE - 7, Long.MAX_VALUE - 8})
    void givenLargeBigDecimalWhenConvertToIntegerThenLosePrecision(long expected) {
        BigDecimal decimal = BigDecimal.valueOf(expected);
        int actual = decimal.intValue();
        assertThat(actual)
          .isNotEqualTo((int) expected)
          .isEqualTo((int) expected - Long.MAX_VALUE - 1);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5})
    void givenLargeBigDecimalWhenConvertToIntegerThenLosePrecision(double given) {
        BigDecimal decimal = BigDecimal.valueOf(given);
        int integerValue = decimal.intValue();
        double actual = Integer.valueOf(integerValue).doubleValue();
        assertThat(actual)
          .isEqualTo((int) given)
          .isNotEqualTo(given);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5})
    void givenLargeBigDecimalWhenConvertToIntegerWithRoundingUpThenLosePrecision(double given) {
        BigDecimal decimal = BigDecimal.valueOf(given);
        int integerValue = decimal.setScale(0, RoundingMode.CEILING).intValue();
        double actual = Integer.valueOf(integerValue).doubleValue();
        assertThat(actual)
          .isEqualTo(Math.ceil(given))
          .isNotEqualTo(given);
    }

    @ParameterizedTest
    @ValueSource(doubles = {
      0.0, 1.00, 2.000, 3.0000,
      4.00000, 5.000000, 6.00000000,
      7.000000000, 8.0000000000})
    void givenLargeBigDecimalWhenCheckScaleThenItGreaterThanZero(double given) {
        BigDecimal decimal = BigDecimal.valueOf(given);
        assertThat(decimal.scale()).isPositive();
        assertThat(decimal.toBigInteger()).isEqualTo((int) given);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Long.MAX_VALUE - 0.5, Long.MAX_VALUE - 1.5, Long.MAX_VALUE - 2.5,
      Long.MAX_VALUE - 3.5, Long.MAX_VALUE - 4.5, Long.MAX_VALUE - 5.5,
      Long.MAX_VALUE - 6.5, Long.MAX_VALUE - 7.5, Long.MAX_VALUE - 8.5})
    void givenLargeBigDecimalWhenConvertToIntegerThenLosePrecisionFromBothSides(double given) {
        BigDecimal decimal = BigDecimal.valueOf(given);
        int integerValue = decimal.intValue();
        double actual = Integer.valueOf(integerValue).doubleValue();
        assertThat(actual)
          .isNotEqualTo(Math.floor(given))
          .isNotEqualTo(given);
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MAX_VALUE, Long.MAX_VALUE - 1, Long.MAX_VALUE - 2,
      Long.MAX_VALUE - 3, Long.MAX_VALUE - 4, Long.MAX_VALUE - 5,
      Long.MAX_VALUE - 6, Long.MAX_VALUE - 7, Long.MAX_VALUE - 8})
    void givenLargeBigDecimalWhenConvertToExactIntegerThenThrowException(long expected) {
        BigDecimal decimal = BigDecimal.valueOf(expected);
        assertThatExceptionOfType(ArithmeticException.class)
          .isThrownBy(decimal::intValueExact);
    }

    @ParameterizedTest
    @ValueSource(longs = {
      Long.MAX_VALUE, Long.MAX_VALUE - 1, Long.MAX_VALUE - 2,
      Long.MIN_VALUE, Long.MIN_VALUE + 1, Long.MIN_VALUE + 2,
      0, 1, 2
    })
    void givenLargeBigDecimalWhenConvertToIntegerThenSetTheMaxOrMinValue(long expected) {
        BigDecimal decimal = BigDecimal.valueOf(expected);
        boolean tooBig = isTooBig(decimal);
        boolean tooSmall = isTooSmall(decimal);
        int actual;
        if (tooBig) {
            actual = Integer.MAX_VALUE;
        } else if (tooSmall) {
            actual = Integer.MIN_VALUE;
        } else {
            actual = decimal.intValue();
        }
        assertThat(tooBig).isEqualTo(actual == Integer.MAX_VALUE);
        assertThat(tooSmall).isEqualTo(actual == Integer.MIN_VALUE);
        assertThat(!tooBig && !tooSmall).isEqualTo(actual == expected);
    }

    private static boolean isTooSmall(BigDecimal decimal) {
        return BigDecimal.valueOf(Integer.MIN_VALUE).compareTo(decimal) > 0;
    }

    private static boolean isTooBig(BigDecimal decimal) {
        return BigDecimal.valueOf(Integer.MAX_VALUE).compareTo(decimal) < 0;
    }
}
