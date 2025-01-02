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
    public void whenCreatedFromFloat_thenMatchesInternallyStoredValue() {
        float floatToConvert = 1.10000002384185791015625f;
        BigDecimal bdFromFloat = new BigDecimal(floatToConvert);
        assertEquals("1.10000002384185791015625", bdFromFloat.toString());
    }

    @Test
    public void whenCreatedFromString_thenPreservesTheOriginal() {
        BigDecimal bdFromString = new BigDecimal("1.1");
        assertEquals("1.1", bdFromString.toString());
    }

    @Test
    public void whenCreatedFromFloatConvertedToString_thenFloatInternalValueGetsTruncated() {
        String floatValue = Float.toString(1.10000002384185791015625f);
        BigDecimal bdFromString = new BigDecimal(floatValue);
        assertEquals("1.1", floatValue);
        assertEquals("1.1", bdFromString.toString());
    }

    @Test
    public void whenCreatedByValueOf_thenFloatValueGetsTruncated() {
        assertEquals("1.100000023841858", BigDecimal.valueOf(1.10000002384185791015625f).toString());
    }

    @Test
    public void whenDoubleConvertsFloatToString_thenFloatValueGetsTruncated() {
        assertEquals("1.100000023841858", Double.toString(1.10000002384185791015625f));
    }

}