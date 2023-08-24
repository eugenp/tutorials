package com.baeldung.javadoublevsbigdecimal;

import com.baeldung.javadoublevsbigdecimal.ConversionExample;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

public class ConversionExampleUnitTest {

    @Test
    void whenConvertingDoubleToBigDecimal_thenConversionIsCorrect() {
        BigDecimal[] results = ConversionExample.performConversions();

        assertEquals(results[0], results[1]);
    }

    @Test
    void whenConvertingBigDecimalToDouble_thenConversionIsCorrect() {
        BigDecimal[] results = ConversionExample.performConversions();

        assertNotEquals(results[2], results[3]);
    }
}
