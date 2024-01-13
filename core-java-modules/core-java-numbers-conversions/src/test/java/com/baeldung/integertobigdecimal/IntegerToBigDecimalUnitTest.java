package com.baeldung.integertobigdecimal;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

class IntegerToBigDecimalUnitTest {

    @ParameterizedTest
    @ArgumentsSource(BigDecimalConversionArgumentsProvider.class)
    void giveIntegerWhenConvertWithConstructorToBigDecimalThenConversionCorrect(Integer given, BigDecimal expected) {
        BigDecimal actual = new BigDecimal(given);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void giveIntegerWhenConvertWithConstructorToBigDecimalThenConversionWithoutCaching(Integer given) {
        BigDecimal firstBigDecimal = new BigDecimal(given);
        BigDecimal secondBigDecimal = new BigDecimal(given);
        assertThat(firstBigDecimal)
          .isEqualTo(secondBigDecimal)
          .isNotSameAs(secondBigDecimal);
    }

    @ParameterizedTest
    @ArgumentsSource(BigDecimalConversionArgumentsProvider.class)
    void giveIntegerWhenConvertWithValueOfToBigDecimalThenConversionCorrect(Integer given, BigDecimal expected) {
        BigDecimal actual = BigDecimal.valueOf(given);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void giveIntegerWhenConvertWithValueOfToBigDecimalThenConversionCachesTheResults(Integer given) {
        BigDecimal firstBigDecimal = BigDecimal.valueOf(given);
        BigDecimal secondBigDecimal = BigDecimal.valueOf(given);
        assertThat(firstBigDecimal).isSameAs(secondBigDecimal);
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21})
    void giveIntegerWhenConvertWithValueOfToBigDecimalThenConversionWontCacheTheResults(Integer given) {
        BigDecimal firstBigDecimal = BigDecimal.valueOf(given);
        BigDecimal secondBigDecimal = BigDecimal.valueOf(given);
        assertThat(firstBigDecimal)
          .isEqualTo(secondBigDecimal)
          .isNotSameAs(secondBigDecimal);
    }
}
