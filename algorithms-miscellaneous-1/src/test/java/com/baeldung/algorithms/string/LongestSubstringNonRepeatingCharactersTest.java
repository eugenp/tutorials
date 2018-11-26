package com.baeldung.algorithms.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongestSubstringNonRepeatingCharactersTest {

    @Test
    void givenString_whenGetNonRepeatingCharactersBruteForceCalled_thenResultFoundAsExpected() {
        String input = "CODINGISAWESOME";
        Assertions.assertEquals("NGISAWE", LongestSubstringNonRepeatingCharacters.getNonRepeatingCharactersBruteForce(input));
    }

    @Test
    void givenString_whenGetNonRepeatingCharactersCalled_thenResultFoundAsExpected() {
        String input = "CODINGISAWESOME";
        Assertions.assertEquals("NGISAWE",LongestSubstringNonRepeatingCharacters.getNonRepeatingCharacters(input));
    }

}
