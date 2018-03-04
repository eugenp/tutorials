package com.baeldung.algorithms.moneywords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.algorithms.numberwordconverter.NumberWordConverter;

public class NumberWordConverterTest {

    @Test
    public void whenMoneyNegative_thenReturnInvalidInput() {
        assertEquals((NumberWordConverter.getMoneyIntoWords(-13)), NumberWordConverter.INVALID_INPUT_GIVEN);
    }

    @Test
    public void whenZeroDollarsGiven_thenReturnEmptyString() {
        assertEquals((NumberWordConverter.getMoneyIntoWords(0)), "");
    }

    @Test
    public void whenOnlyDollarsGiven_thenReturnWords() {
        assertEquals((NumberWordConverter.getMoneyIntoWords(1)), "one dollar");
    }

    @Test
    public void whenOnlyCentsGiven_thenReturnWords() {
        assertEquals((NumberWordConverter.getMoneyIntoWords(0.6)), "sixty cents");
    }

    @Test
    public void whenGivenDollarsAndCents_thenReturnWords() {
        String expectedResult = "nine hundred twenty four dollars and sixty cents";
        assertEquals((NumberWordConverter.getMoneyIntoWords(924.6)), expectedResult);
    }

    @Test
    public void whenGivenDollarsAndCents_thenReturnWordsVersionTwo() {
        assertEquals((NumberWordConverter.getMoneyIntoWords("310")), "three hundred ten £ 00/100");
    }
}
