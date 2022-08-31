package com.baeldung.algorithms.moneywords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.algorithms.numberwordconverter.NumberWordConverter;

public class NumberWordConverterUnitTest {

    @Test
    public void whenMoneyNegative_thenReturnInvalidInput() {
        assertEquals(NumberWordConverter.INVALID_INPUT_GIVEN, NumberWordConverter.getMoneyIntoWords(-13));
    }

    @Test
    public void whenZeroDollarsGiven_thenReturnEmptyString() {
        assertEquals("", NumberWordConverter.getMoneyIntoWords(0));
    }

    @Test
    public void whenOnlyDollarsGiven_thenReturnWords() {
        assertEquals("one dollar", NumberWordConverter.getMoneyIntoWords(1));
    }

    @Test
    public void whenOnlyCentsGiven_thenReturnWords() {
        assertEquals("sixty cents", NumberWordConverter.getMoneyIntoWords(0.6));
    }
    
    @Test
    public void whenAlmostAMillioDollarsGiven_thenReturnWords() {
        String expectedResult = "nine hundred ninety nine thousand nine hundred ninety nine dollars";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(999_999));
    }
    
    @Test
    public void whenThirtyMillionDollarsGiven_thenReturnWords() {
        String expectedResult = "thirty three million three hundred forty eight thousand nine hundred seventy eight dollars";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(33_348_978));
    }
    
    @Test
    public void whenTwoBillionDollarsGiven_thenReturnWords() {
        String expectedResult = "two billion one hundred thirty three million two hundred forty seven thousand eight hundred ten dollars";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(2_133_247_810));
    }

    @Test
    public void whenGivenDollarsAndCents_thenReturnWords() {
        String expectedResult = "nine hundred twenty four dollars and sixty cents";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(924.6));
    }

    @Test
    public void whenOneDollarAndNoCents_thenReturnDollarSingular() {
        assertEquals("one dollar", NumberWordConverter.getMoneyIntoWords(1));
    }

    @Test
    public void whenNoDollarsAndOneCent_thenReturnCentSingular() {
        assertEquals("one cent", NumberWordConverter.getMoneyIntoWords(0.01));
    }

    @Test
    public void whenNoDollarsAndTwoCents_thenReturnCentsPlural() {
        assertEquals("two cents", NumberWordConverter.getMoneyIntoWords(0.02));
    }

    @Test
    public void whenNoDollarsAndNinetyNineCents_thenReturnWords() {
        assertEquals("ninety nine cents", NumberWordConverter.getMoneyIntoWords(0.99));
    }

    @Test
    public void whenNoDollarsAndNineFiveNineCents_thenCorrectRounding() {
        assertEquals("ninety six cents", NumberWordConverter.getMoneyIntoWords(0.959));
    }

    @Test
    public void whenGivenDollarsAndCents_thenReturnWordsVersionTwo() {
        assertEquals("three hundred ten £ 00/100", NumberWordConverter.getMoneyIntoWords("310"));
    }
}
