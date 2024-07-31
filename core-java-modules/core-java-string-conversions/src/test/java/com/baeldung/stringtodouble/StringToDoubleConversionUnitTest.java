package com.baeldung.stringtodouble;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.junit.Test;

public class StringToDoubleConversionUnitTest {

    @Test
    public void givenValidString_WhenParseDouble_ThenResultIsPrimitiveDouble() {
        assertEquals(1.23, Double.parseDouble("1.23"), 0.000001);
    }
    
    @Test(expected = NullPointerException.class)
    public void givenNullString_WhenParseDouble_ThenNullPointerExceptionIsThrown() {
        Double.parseDouble(null);
    }
    
    @Test(expected = NumberFormatException.class)
    public void givenInalidString_WhenParseDouble_ThenNumberFormatExceptionIsThrown() {
        Double.parseDouble("&");
    }
    
    @Test
    public void givenValidString_WhenValueOf_ThenResultIsPrimitiveDouble() {
        assertEquals(1.23, Double.valueOf("1.23"), 0.000001);
    }
    
    @Test(expected = NullPointerException.class)
    public void givenNullString_WhenValueOf_ThenNullPointerExceptionIsThrown() {
        Double.valueOf(null);
    }
    
    @Test(expected = NumberFormatException.class)
    public void givenInalidString_WhenValueOf_ThenNumberFormatExceptionIsThrown() {
        Double.valueOf("&");
    }
    
    @Test
    public void givenValidString_WhenDecimalFormat_ThenResultIsValidDouble() throws ParseException {
        assertEquals(1.23, new DecimalFormat("#").parse("1.23").doubleValue(), 0.000001);
    }
    
    @Test(expected = NullPointerException.class)
    public void givenNullString_WhenDecimalFormat_ThenNullPointerExceptionIsThrown() throws ParseException {
        new DecimalFormat("#").parse(null);
    }
    
    @Test(expected = ParseException.class)
    public void givenInvalidString_WhenDecimalFormat_ThenParseExceptionIsThrown() throws ParseException {
        new DecimalFormat("#").parse("&");
    }
}
