package com.baeldung.exception.numberformat;

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
public class NumberFormatExceptionUnitTest {

    Logger LOG = Logger.getLogger(NumberFormatExceptionUnitTest.class.getName());

    /* ---INTEGER FAIL CASES--- */
    @Test(expected = NumberFormatException.class)
    public void givenByteConstructor_whenAlphabetAsInput_thenFail() {
        Byte byteInt = new Byte("one");
    }

    @Test(expected = NumberFormatException.class)
    public void givenShortConstructor_whenSpaceInInput_thenFail() {
        Short shortInt = new Short("2 ");
    }

    @Test(expected = NumberFormatException.class)
    public void givenParseIntMethod_whenSpaceInInput_thenFail() {
        Integer aIntPrim = Integer.parseInt("6000 ");
    }

    @Test(expected = NumberFormatException.class)
    public void givenParseIntMethod_whenUnderscoreInInput_thenFail() {
        int bIntPrim = Integer.parseInt("_6000");
    }

    @Test(expected = NumberFormatException.class)
    public void givenIntegerValueOfMethod_whenCommaInInput_thenFail() {
        Integer cIntPrim = Integer.valueOf("6,000");
    }

    @Test(expected = NumberFormatException.class)
    public void givenBigIntegerConstructor_whenDecimalInInput_thenFail() {
        BigInteger bigInteger = new BigInteger("4.0");
    }

    @Test(expected = NumberFormatException.class)
    public void givenDecodeMethod_whenAlphabetInInput_thenFail() {
        Long decodedLong = Long.decode("64403L");
    }

    /* ---INTEGER PASS CASES--- */
    @Test
    public void givenInvalidNumberInputs_whenOptimized_thenPass() {
        Byte byteInt = new Byte("1");
        assertEquals(1, byteInt.intValue());

        Short shortInt = new Short("2 ".trim());
        assertEquals(2, shortInt.intValue());

        Integer aIntObj = Integer.valueOf("6");
        assertEquals(6, aIntObj.intValue());

        BigInteger bigInteger = new BigInteger("4");
        assertEquals(4, bigInteger.intValue());

        int aIntPrim = Integer.parseInt("6000 ".trim());
        assertEquals(6000, aIntPrim);

        int bIntPrim = Integer.parseInt("_6000".replaceAll("_", ""));
        assertEquals(6000, bIntPrim);

        int cIntPrim = Integer.parseInt("-6000");
        assertEquals(-6000, cIntPrim);

        Long decodeInteger = Long.decode("644032334");
        assertEquals(644032334L, decodeInteger.longValue());
    }

    /* ---DOUBLE FAIL CASES--- */
    @Test(expected = NumberFormatException.class)
    public void givenFloatConstructor_whenAlphabetInInput_thenFail() {
        Float floatDecimalObj = new Float("one.1");
    }

    @Test(expected = NumberFormatException.class)
    public void givenDoubleConstructor_whenAlphabetInInput_thenFail() {
        Double doubleDecimalObj = new Double("two.2");
    }

    @Test(expected = NumberFormatException.class)
    public void givenBigDecimalConstructor_whenSpecialCharsInInput_thenFail() {
        BigDecimal bigDecimalObj = new BigDecimal("3_0.3");
    }

    @Test(expected = NumberFormatException.class)
    public void givenParseDoubleMethod_whenCommaInInput_thenFail() {
        double aDoublePrim = Double.parseDouble("4000,1");
    }

    /* ---DOUBLE PASS CASES--- */
    @Test
    public void givenDoubleConstructor_whenDecimalInInput_thenPass() {
        Double doubleDecimalObj = new Double("2.2");
        assertEquals(2.2, doubleDecimalObj.doubleValue(), 0);
    }

    @Test
    public void givenDoubleValueOfMethod_whenMinusInInput_thenPass() {
        Double aDoubleObj = Double.valueOf("-6000");
        assertEquals(-6000, aDoubleObj.doubleValue(), 0);
    }

    @Test
    public void givenUsDecimalNumber_whenParsedWithNumberFormat_thenPass() throws ParseException {
        Number parsedNumber = parseNumberWithLocale("4000.1", Locale.US);
        assertEquals(4000.1, parsedNumber);
        assertEquals(4000.1, parsedNumber.doubleValue(), 0);
        assertEquals(4000, parsedNumber.intValue());
    }

    /**
     * In most European countries (for example, France), comma is used as decimal in place of period.
     * @throws ParseException if the input string contains special characters other than comma or decimal. 
     * In this test case, anything after decimal (period) is dropped when a European locale is set.
     */
    @Test
    public void givenEuDecimalNumberHasComma_whenParsedWithNumberFormat_thenPass() throws ParseException {
        Number parsedNumber = parseNumberWithLocale("4000,1", Locale.FRANCE);
        LOG.info("Number parsed is: " + parsedNumber);
        assertEquals(4000.1, parsedNumber);
        assertEquals(4000.1, parsedNumber.doubleValue(), 0);
        assertEquals(4000, parsedNumber.intValue());
    }

    /**
     * Converts a string into a number retaining all decimals, and symbols valid in a locale.
     * @param number the input string for a number.
     * @param locale the locale to consider while parsing a number.
     * @return the generic number object which can represent multiple number types.
     * @throws ParseException when input contains invalid characters.
     */
    private Number parseNumberWithLocale(String number, Locale locale) throws ParseException {
        locale = locale == null ? Locale.getDefault() : locale;
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        return numberFormat.parse(number);
    }

}
