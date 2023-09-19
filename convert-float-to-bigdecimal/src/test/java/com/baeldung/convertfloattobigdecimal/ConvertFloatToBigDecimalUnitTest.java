package com.baeldung.convertfloattobigdecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ConvertFloatToBigDecimalUnitTest {

    @Test
    public void whenFloatComparedWithDifferentValues_thenCouldMatch() {
        assertNotEquals(1.1f, 1.09f);
        assertEquals(1.1f, 1.09999999f);
    }

    @Test
    public void whenCreatedFromFloat_thenCouldNotMatch() {
        float floatToConvert = 0.5f;
        BigDecimal bdFromFloat = new BigDecimal(floatToConvert);
        assertEquals("0.5", bdFromFloat.toString());
        floatToConvert = 1.1f;
        bdFromFloat = new BigDecimal(floatToConvert);
        assertNotEquals("1.1", bdFromFloat.toString());
    }

    @Test
    public void whenCreatedFromString_thenMatches() {
        String floatValue = Float.toString(1.1f);
        BigDecimal bdFromString = new BigDecimal(floatValue);
        assertEquals("1.1", bdFromString.toString());
    }

    @Test
    public void whenCreatedByValueOfAndIsFloat_thenDoesNotMatch() {
        float floatToConvert = 1.1f;
        BigDecimal bdByValueOf = BigDecimal.valueOf(floatToConvert);
        assertNotEquals("1.1", bdByValueOf.toString());
    }

    @Test
    public void whenCreatedByValueOfAndIsDouble_thenMatches() {
        double doubleToConvert = 1.1d;
        BigDecimal bdByValueOf = BigDecimal.valueOf(doubleToConvert);
        assertEquals("1.1", bdByValueOf.toString());
    }
}
