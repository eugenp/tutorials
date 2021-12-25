package com.baeldung.stringtofloat;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringToFloatConversionUnitTest {

    @Test
    public void givenString_whenParseFloat_shouldConvertToFloat() {
        String givenString = "1.25";

        float result = Float.parseFloat(givenString);

        assertEquals(1.25f, result);
    }

    @Test
    public void givenString_whenValueOf_shouldConvertToFloat() {
        String givenString = "1.25";

        Float result = Float.valueOf(givenString);

        assertEquals(1.25f, result);
    }

    @Test
    public void givenFloat_whenStringConcatenation_shouldConvertToString() {
        float givenFloat = 1.25f;

        String result = givenFloat + "";

        assertEquals("1.25", result);
    }

    @Test
    public void givenNullFloat_whenStringConcatenation_shouldConvertToNullString() {
        Float givenFloat = null;

        String result = givenFloat + "";

        assertEquals("null", result);
    }

    @Test
    public void givenFloat_whenToString_shouldConvertToString() {
        Float givenFloat = 1.25f;

        String result = Float.toString(givenFloat);

        assertEquals("1.25", result);
    }

    @Test
    public void givenNullFloat_whenToString_shouldThrowNullPointerException() {
        Float givenFloat = null;

        assertThrows(NullPointerException.class, () -> Float.toString(givenFloat));
    }

    @Test
    public void givenFloat_whenValueOf_shouldConvertToString() {
        Float givenFloat = 1.25f;

        String result = String.valueOf(givenFloat);

        assertEquals("1.25", result);
    }

    @Test
    public void givenNullFloat_whenValueOf_shouldConvertToNullString() {
        Float givenFloat = null;

        String result = String.valueOf(givenFloat);

        assertEquals("null", result);
    }

    @Test
    public void givenFloat_whenDecimalFormat_shouldConvertToString() {
        Float givenFloat = 1.25f;

        String result = new DecimalFormat("#.0000").format(givenFloat);

        assertEquals("1.2500", result);
    }

    @Test
    public void givenNullFloat_whenDecimalFormat_shouldThrowIllegalArgumentException() {
        Float givenFloat = null;

        assertThrows(IllegalArgumentException.class, () -> new DecimalFormat("#.000").format(givenFloat));
    }
}