package com.baeldung.algorithms.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongestSubstringNonRepeatingCharactersTest {

    @Test
    void givenString_whenGetUniqueCharacterSubstringBruteForceCalled_thenResultFoundAsExpected() {
        String input = "CODINGISAWESOME";
        Assertions.assertEquals("NGISAWE", LongestSubstringNonRepeatingCharacters.getUniqueCharacterSubstringBruteForce(input));
    }

    @Test
    void givenString_whenGetUniqueCharacterSubstringCalled_thenResultFoundAsExpected() {
        String input = "CODINGISAWESOME";
        Assertions.assertEquals("NGISAWE",LongestSubstringNonRepeatingCharacters.getUniqueCharacterSubstring(input));
    }

}
