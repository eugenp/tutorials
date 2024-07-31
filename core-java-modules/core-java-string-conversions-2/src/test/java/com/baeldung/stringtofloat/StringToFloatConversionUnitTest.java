package com.baeldung.stringtofloat;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringToFloatConversionUnitTest {

    @Test
    public void givenFloat_whenStringConcatenation_thenReturnString() {
        Float givenFloat = 1.25f;

        String result = givenFloat + "";

        assertEquals("1.25", result);
    }

    @Test
    public void givenFloatPrimitive_whenStringConcatenation_thenReturnString() {
        float givenFloat = 1.25f;

        String result = givenFloat + "";

        assertEquals("1.25", result);
    }

    @Test
    public void givenNullFloat_whenStringConcatenation_thenReturnNullString() {
        Float givenFloat = null;

        String result = givenFloat + "";

        assertEquals("null", result);
    }

    @Test
    public void givenFloat_whenToString_thenReturnString() {
        Float givenFloat = 1.25f;

        String result = Float.toString(givenFloat);

        assertEquals("1.25", result);
    }

    @Test
    public void givenNullFloat_whenToString_thenThrowNullPointerException() {
        Float givenFloat = null;

        assertThrows(NullPointerException.class, () -> Float.toString(givenFloat));
    }

    @Test
    public void givenFloat_whenValueOf_thenReturnString() {
        Float givenFloat = 1.25f;

        String result = String.valueOf(givenFloat);

        assertEquals("1.25", result);
    }

    @Test
    public void givenNullFloat_whenValueOf_thenReturnNullString() {
        Float givenFloat = null;

        String result = String.valueOf(givenFloat);

        assertEquals("null", result);
    }

    @Test
    public void givenFloat_whenDecimalFormat_thenReturnString() {
        Float givenFloat = 1.25f;

        String result = new DecimalFormat("#.0000").format(givenFloat);

        assertEquals("1.2500", result);
    }

    @Test
    public void givenFloat_whenDecimalFormat_thenReturnWholeNumberString() {
        Float givenFloat = 1.0025f;

        String result = new DecimalFormat("#.##").format(givenFloat);

        assertEquals("1", result);
    }

    @Test
    public void givenNullFloat_whenDecimalFormat_thenThrowIllegalArgumentException() {
        Float givenFloat = null;

        assertThrows(IllegalArgumentException.class, () -> new DecimalFormat("#.000").format(givenFloat));
    }

    @Test
    public void givenFloat_whenStringFormat_thenReturnString() {
        Float givenFloat = 1.25f;

        String result = String.format("%f", givenFloat);

        assertEquals("1.250000", result);
    }

    @Test
    public void givenFloat_whenStringFormatWithDecimalLimit_thenReturnRoundedString() {
        Float givenFloat = 1.256f;

        String result = String.format("%.2f", givenFloat);

        assertEquals("1.26", result);
    }

    @Test
    public void givenNullFloat_whenStringFormatWithDecimalLimit_thenReturnNullString() {
        Float givenFloat = null;

        String result = String.format("%f", givenFloat);

        assertEquals("null", result);
    }

    @Test
    public void givenString_whenParseFloat_thenReturnFloat() {
        String givenString = "1.25";

        float result = Float.parseFloat(givenString);

        assertEquals(1.25f, result);
    }

    @Test
    public void givenNullString_whenParseFloat_thenThrowNullPointerException() {
        String givenString = null;

        assertThrows(NullPointerException.class, () -> Float.parseFloat(givenString));
    }

    @Test
    public void givenNonParsableString_whenParseFloat_thenThrowNumberFormatException() {
        String givenString = "1.23x";

        assertThrows(NumberFormatException.class, () -> Float.parseFloat(givenString));
    }

    @Test
    public void givenString_whenValueOf_thenReturnFloat() {
        String givenString = "1.25";

        Float result = Float.valueOf(givenString);

        assertEquals(1.25f, result);
    }

    @Test
    public void givenNonParsableString_whenValueOf_thenThrowNumberFormatException() {
        String givenString = "1.25x";

        assertThrows(NumberFormatException.class, () -> Float.valueOf(givenString));
    }

    @Test
    public void givenString_whenConstructor_thenReturnFloat() {
        String givenString = "1.25";

        Float result = new Float(givenString);

        assertEquals(1.25f, result);
    }

    @Test
    public void givenString_whenDecimalFormat_thenReturnFloat() throws ParseException {
        String givenString = "1,250";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#.000");
        decimalFormat.setDecimalFormatSymbols(symbols);

        Float result = decimalFormat.parse(givenString).floatValue();

        assertEquals(1.25f, result);
    }
}