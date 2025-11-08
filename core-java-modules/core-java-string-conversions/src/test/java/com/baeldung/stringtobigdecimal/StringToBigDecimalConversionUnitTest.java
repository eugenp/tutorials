package com.baeldung.stringtobigdecimal;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class StringToBigDecimalConversionUnitTest {

    @Test
    public void givenValidString_WhenBigDecimalObjectWithStringParameter_ThenResultIsDecimalObject() {
        BigDecimal bigDecimal = new BigDecimal("123");
        assertEquals(new BigDecimal(123), bigDecimal);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullString_WhenBigDecimalObjectWithStringParameter_ThenNullPointerExceptionIsThrown() {
        String bigDecimal = null;
        new BigDecimal(bigDecimal);
    }

    @Test(expected = NumberFormatException.class)
    public void givenInalidString_WhenBigDecimalObjectWithStringParameter_ThenNumberFormatExceptionIsThrown() {
        new BigDecimal("&");
    }

    @Test
    public void givenValidString_WhenValueOfDoubleFromString_ThenResultIsDecimalObject() {
        BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf("123.42"));
        assertEquals(new BigDecimal(123.42).setScale(2, BigDecimal.ROUND_HALF_UP), bigDecimal);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullString_WhenValueOfDoubleFromString_ThenNullPointerExceptionIsThrown() {
        BigDecimal.valueOf(Double.valueOf(null));
    }

    @Test(expected = NumberFormatException.class)
    public void givenInalidString_WhenValueOfDoubleFromString_ThenNumberFormatExceptionIsThrown() {
        BigDecimal.valueOf(Double.valueOf("&"));
    }

    @Test
    public void givenValidString_WhenDecimalFormatOfString_ThenResultIsDecimalObject() throws ParseException {
        BigDecimal bigDecimal = new BigDecimal(10692467440017.111).setScale(3, BigDecimal.ROUND_HALF_UP);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        // parse the string value
        BigDecimal parsedStringValue = (BigDecimal) decimalFormat.parse("10,692,467,440,017.111");

        assertEquals(bigDecimal, parsedStringValue);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullString_WhenDecimalFormatOfString_ThenNullPointerExceptionIsThrown() throws ParseException {
        new DecimalFormat("#").parse(null);
    }

    @Test(expected = ParseException.class)
    public void givenInalidString_WhenDecimalFormatOfString_ThenNumberFormatExceptionIsThrown() throws ParseException {
        new DecimalFormat("#").parse("&");
    }

    @Test
    public void givenStringWithDollarSign_whenRemovedAndConvertedToBigDecimal_thenReturnExpectedValue() {
        String salePrice = "$348.45";
        String price = salePrice.replace("$", "");
        BigDecimal amount = new BigDecimal(price);

        assertEquals(new BigDecimal("348.45"), amount);
    }

    @Test
    public void givenStringWithDollarSign_whenParsedWithNumberFormat_thenReturnExpectedValue() throws ParseException {
        String salePrice = "$348.45";
        Locale locale = Locale.US;

        Number number = NumberFormat.getCurrencyInstance(locale).parse(salePrice);
        BigDecimal amount = new BigDecimal(number.toString());

        assertEquals(new BigDecimal("348.45"), amount);
    }
}
