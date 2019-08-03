package com.baeldung.string.todouble;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
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
        
        DecimalFormat format = new DecimalFormat("\u00A4#,##0.00");
        format.setParseBigDecimal(true);
        
        BigDecimal decimal = (BigDecimal) format.parse("-$1,000.57");
        
        assertEquals(-1000.57, decimal.doubleValue(), 0.000001);
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
