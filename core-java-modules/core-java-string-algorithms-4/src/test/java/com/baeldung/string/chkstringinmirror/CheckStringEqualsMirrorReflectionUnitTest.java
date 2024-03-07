package com.baeldung.string.chkstringinmirror;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class CheckStringEqualsMirrorReflectionUnitTest {

    private final static Set<Character> SYMMETRIC_LETTERS = Set.of('A', 'H', 'I', 'M', 'O', 'T', 'U', 'V', 'W', 'X', 'Y');

    @Test
    void whenCallingIsReflectionEqual_thenGetExpectedResults() {
        assertFalse(isMirrorImageEqual("LOL"));
        assertFalse(isMirrorImageEqual("AXY"));
        assertFalse(isMirrorImageEqual("HUHU"));

        assertTrue(isMirrorImageEqual(""));
        assertTrue(isMirrorImageEqual("AAA"));
        assertTrue(isMirrorImageEqual("HUH"));
        assertTrue(isMirrorImageEqual("HIMMIH"));
        assertTrue(isMirrorImageEqual("HIMIH"));
    }


    public boolean isMirrorImageEqual(String input) {
        return containsOnlySymmetricLetters(input) && isPalindrome(input);
    }

    private boolean containsOnlySymmetricLetters(String input) {
        Set<Character> characterSet = input.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toSet());
        characterSet.removeAll(SYMMETRIC_LETTERS);
        return characterSet.isEmpty();
    }

    private boolean isPalindrome(String input) {
        String reversed = new StringBuilder(input).reverse()
            .toString();
        return input.equals(reversed);
    }

}