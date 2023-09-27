package com.baeldung.javadoublevsbigdecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

public class BigDecimalConversionUnitTest {

    @Test
    void whenConvertingDoubleToBigDecimal_thenConversionIsCorrect() {
        double doubleValue = 123.456;
        BigDecimal bigDecimalValue = BigDecimal.valueOf(doubleValue);
        BigDecimal expected = new BigDecimal("123.456").setScale(3, RoundingMode.HALF_UP);
        assertEquals(expected, bigDecimalValue.setScale(3, RoundingMode.HALF_UP));
    }

    @Test
    void whenConvertingBigDecimalToDouble_thenConversionIsCorrect() {
        BigDecimal bigDecimalValue = new BigDecimal("789.123456789");
        double doubleValue = bigDecimalValue.doubleValue();
        BigDecimal convertedBackToBigDecimal = BigDecimal.valueOf(doubleValue);

        if (!bigDecimalValue.equals(convertedBackToBigDecimal)) {
            throw new AssertionError("Potential data loss during conversion!");
        }

        assertEquals(bigDecimalValue, convertedBackToBigDecimal);
    }
}
