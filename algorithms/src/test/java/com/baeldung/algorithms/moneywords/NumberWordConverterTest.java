package com.baeldung.algorithms.moneywords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.algorithms.numberwordconverter.NumberWordConverter;

public class NumberWordConverterTest {

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
    public void whenGivenDollarsAndCents_thenReturnWords() {
        String expectedResult = "nine hundred twenty four dollars and sixty cents";
        assertEquals(expectedResult, NumberWordConverter.getMoneyIntoWords(924.6));
    }

    @Test
    public void whenGivenDollarsAndCents_thenReturnWordsVersionTwo() {
        assertEquals("three hundred ten £ 00/100", NumberWordConverter.getMoneyIntoWords("310"));
    }
}
