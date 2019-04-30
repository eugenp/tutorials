/**
 * 
 */
package com.baledung.exception.numberformat;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Logger;

import org.junit.Test;

/**
 * A set of examples tested to show cases where NumberFormatException is thrown and not thrown.
 */
public class NumberFormatExceptionTestExamples {

    Logger LOG = Logger.getLogger(NumberFormatExceptionTestExamples.class.getName());

    /* ---INTEGER FAIL CASES--- */
    @Test(expected = NumberFormatException.class)
    public void testByteConstructorShouldFailAlphabet() {
        Byte byteInt = new Byte("one");
    }

    @Test(expected = NumberFormatException.class)
    public void testShortConstructorShouldFailSpace() {
        Short shortInt = new Short("2 ");
    }

    @Test(expected = NumberFormatException.class)
    public void testParseIntShouldFailSpace() {
        Integer aIntPrim = Integer.parseInt("6000 ");
    }

    @Test(expected = NumberFormatException.class)
    public void testParseIntShouldFailUnderscore() {
        int bIntPrim = Integer.parseInt("6_000");
    }

    @Test(expected = NumberFormatException.class)
    public void testIntegerValueOfShouldFailComma() {
        Integer cIntPrim = Integer.valueOf("6,000");
    }

    @Test(expected = NumberFormatException.class)
    public void testBigIntegerConstructorShouldFailDecimal() {
        BigInteger bigInteger = new BigInteger("4.0");
    }

    @Test(expected = NumberFormatException.class)
    public void testLongDecodeShouldFailAlphabet() {
        Long decodeInteger = Long.decode("64403L");
    }

    /* ---INTEGER PASS CASES--- */
    @Test
    public void testIntegerPass() {
        Byte byteInt = new Byte("1");
        Short shortInt = new Short("2 ".trim());
        Integer aIntObj = Integer.valueOf("6");
        BigInteger bigInteger = new BigInteger("4");
        int aIntPrim = Integer.parseInt("6000 ".trim());
        int bIntPrim = Integer.parseInt("6_000".replaceAll("_", ""));
        int cIntPrim = Integer.parseInt("-6000");
        Long decodeInteger = Long.decode("64403");
    }

    /* ---DOUBLE FAIL CASES--- */
    @Test(expected = NumberFormatException.class)
    public void testFloatConstructorShouldFailAlphabet() {
        Float floatDecimalObj = new Float("one.1");
    }

    @Test(expected = NumberFormatException.class)
    public void testDoubleConstructorShouldFailCharAlphabet() {
        Double doubleDecimalObj = new Double("two.2");
    }

    @Test(expected = NumberFormatException.class)
    public void testBigDecimalConstructorShouldFailSpecialChar() {
        BigDecimal bigDecimalObj = new BigDecimal("3_0.3");
    }

    @Test(expected = NumberFormatException.class)
    public void testParseDoubleShouldFailCharComma() {
        double aDoublePrim = Double.parseDouble("4000,1");
    }

    /* ---DOUBLE PASS CASES--- */
    @Test
    public void testDoubleConstructorShouldPassDecimal() {
        Double doubleDecimalObj = new Double("2.2");
        assert doubleDecimalObj == 2.2;
    }

    @Test
    public void testDoubleValueOfShouldPassMinus() {
        Double aDoubleObj = Double.valueOf("-6000");
        assert aDoubleObj == -6000;
    }

    @Test
    public void testParseNumberInALocaleShouldPassDecimalUsa() throws ParseException {
        Number parsedNumber = parseNumberWithLocale("4000.1", Locale.US);
        assertEquals(4000.1, parsedNumber);
        assert parsedNumber.doubleValue() == 4000.1;
        assert parsedNumber.intValue() == 4000;
    }

    /**
     * In most European countries (for example, France), comma is used as decimal in place of period.
     * @throws ParseException if the input string contains special characters other than comma or decimal. 
     * In this test case, anything after decimal (period) is dropped when a European locale is set.
     */
    @Test
    public void testParseNumberInALocaleShouldPassCommaAsDecimalEu() throws ParseException {
        Number parsedNumber = parseNumberWithLocale("4000,1", Locale.FRANCE);
        LOG.info("Number parsed is: " + parsedNumber);
        assert parsedNumber.doubleValue() == 4000.1;
        assert parsedNumber.intValue() == 4000;
    }

    /**
     * Converts a string into a number retaining all decimals, and symbols valid in a locale.
     * @param number the input string for a number.
     * @param locale the locale to consider while parsing a number.
     * @return the generic number object which can represent multiple number types.
     * @throws ParseException when input contains invalid characters
     */
    private Number parseNumberWithLocale(String number, Locale locale) throws ParseException {
        locale = locale == null ? Locale.getDefault() : locale;
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        return numberFormat.parse(number);
    }

}
