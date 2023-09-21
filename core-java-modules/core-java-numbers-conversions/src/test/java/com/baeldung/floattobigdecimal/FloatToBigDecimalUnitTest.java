package com.baeldung.floattobigdecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class FloatToBigDecimalUnitTest {

    @Test
    public void whenFloatComparedWithDifferentValues_thenCouldMatch() {
        assertNotEquals(1.1f, 1.09f);
        assertEquals(1.1f, 1.09999999f);
    }

    @Test
    public void whenCreatedFromCertainFloatValues_thenMatches() {
        float floatToConvert = 0.5f;
        BigDecimal bdFromFloat = new BigDecimal(floatToConvert);
        assertEquals("0.5", bdFromFloat.toString());
    }

    @Test
    public void whenCreatedFromCertainFloatValues_thenDoesNotMatch() {
        float floatToConvert = 1.1f;
        BigDecimal bdFromFloat = new BigDecimal(floatToConvert);
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
    public void whenFloatCastToDouble_thenGotADifferentNumber() {
        float floatToConvert = 1.1f;
        double doubleCast = floatToConvert;
        assertEquals("1.100000023841858", Double.toString(doubleCast));
    }

    @Test
    public void whenCreatedByValueOfAndIsDouble_thenMatches() {
        double doubleToConvert = 1.1d;
        BigDecimal bdByValueOf = BigDecimal.valueOf(doubleToConvert);
        assertEquals("1.1", bdByValueOf.toString());
    }

}