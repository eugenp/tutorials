package com.baeldung.algorithms.moneywords;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.numberwordconverter.NumberWordConverter;

class NumberWordConverterUnitTest {

    @Test
    void whenMoneyNegative_thenReturnInvalidInput() {
        assertEquals(NumberWordConverter.INVALID_INPUT_GIVEN, NumberWordConverter.getMoneyIntoWords(-13));
    }

    @Test
    void whenZeroDollarsGiven_thenReturnEmptyString() {
        assertEquals("", NumberWordConverter.getMoneyIntoWords(0));
    }

    @Test
    void whenOnlyDollarsGiven_thenReturnWords() {
        assertEquals("one dollar", NumberWordConverter.getMoneyIntoWords(1));
    }

    @Test
    void whenOnlyCentsGiven_thenReturnWords() {
        assertEquals("sixty cents", NumberWordConverter.getMoneyIntoWords(0.6));
    }
    
    @Test
    void whenAlmostAMillioDollarsGiven_thenReturnWords() {
        String expectedResult = "nine hundred ninety nine thousand nine hundred ninety nine dollars";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(999_999));
    }
    
    @Test
    void whenThirtyMillionDollarsGiven_thenReturnWords() {
        String expectedResult = "thirty three million three hundred forty eight thousand nine hundred seventy eight dollars";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(33_348_978));
    }
    
    @Test
    void whenTwoBillionDollarsGiven_thenReturnWords() {
        String expectedResult = "two billion one hundred thirty three million two hundred forty seven thousand eight hundred ten dollars";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(2_133_247_810));
    }

    @Test
    void whenGivenDollarsAndCents_thenReturnWords() {
        String expectedResult = "nine hundred twenty four dollars and sixty cents";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(924.6));
    }

    @Test
    void whenOneDollarAndNoCents_thenReturnDollarSingular() {
        assertEquals("one dollar", NumberWordConverter.getMoneyIntoWords(1));
    }

    @Test
    void whenNoDollarsAndOneCent_thenReturnCentSingular() {
        assertEquals("one cent", NumberWordConverter.getMoneyIntoWords(0.01));
    }

    @Test
    void whenNoDollarsAndTwoCents_thenReturnCentsPlural() {
        assertEquals("two cents", NumberWordConverter.getMoneyIntoWords(0.02));
    }

    @Test
    void whenNoDollarsAndNinetyNineCents_thenReturnWords() {
        assertEquals("ninety nine cents", NumberWordConverter.getMoneyIntoWords(0.99));
    }

    @Test
    void whenNoDollarsAndNineFiveNineCents_thenCorrectRounding() {
        assertEquals("ninety six cents", NumberWordConverter.getMoneyIntoWords(0.959));
    }

    @Test
    void whenGivenDollarsAndCents_thenReturnWordsVersionTwo() {
        assertEquals("three hundred ten £ 00/100", NumberWordConverter.getMoneyIntoWords("310"));
    }
}
