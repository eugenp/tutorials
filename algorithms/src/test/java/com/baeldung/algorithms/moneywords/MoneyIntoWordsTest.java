package com.baeldung.algorithms.moneywords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoneyIntoWordsTest {

    private final MoneyIntoWords moneyIntoWords = new MoneyIntoWords();

    @Test
    public void whenMoneyNegative_thenReturnEmptyString() {
        assertEquals((moneyIntoWords.getMoneyIntoWords(-13)), "");
    }

    @Test
    public void whenZeroDollarsGiven_thenReturnZero() {
        assertEquals((moneyIntoWords.getMoneyIntoWords(0)), "zero dollars");
    }

    @Test
    public void whenOnlyDollarsGiven_thenReturnWords() {
        assertEquals((moneyIntoWords.getMoneyIntoWords(1)), "one dollar");
    }

    @Test
    public void whenOnlyCentsGiven_thenReturnWords() {
        assertEquals((moneyIntoWords.getMoneyIntoWords(0.6)), "sixty cents");
    }

    @Test
    public void whenGivenDollarsAndCents_thenReturnWords() {
        String expectedResult = "nine hundred twenty four dollars and sixty cents";
        assertEquals((moneyIntoWords.getMoneyIntoWords(924.6)), expectedResult);
    }

    @Test
    public void whenGivenDollarsAndCents_thenReturnWordsVersionTwo() {
        assertEquals((moneyIntoWords.getMoneyIntoWords("310")), "three hundred ten £ 00/100");
    }

}
