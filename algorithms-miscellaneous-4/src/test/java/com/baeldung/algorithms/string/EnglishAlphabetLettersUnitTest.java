package com.baeldung.algorithms.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnglishAlphabetLettersUnitTest {

    @Test
    void givenString_whenContainsAllCharacter_thenTrue() {
        String input = "Farmer jack realized that big yellow quilts were expensive";
        Assertions.assertTrue(EnglishAlphabetLetters.checkStringForAllTheLetters(input));
    }

    @Test
    void givenString_whenContainsAllCharacter_thenUsingStreamExpectTrue() {
        String input = "Farmer jack realized that big yellow quilts were expensive";
        Assertions.assertTrue(EnglishAlphabetLetters.checkStringForAllLetterUsingStream(input));
    }

}
