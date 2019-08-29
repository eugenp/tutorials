package com.baeldung.string.repetition;

import static com.baeldung.string.repetition.SubstringRepetition.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class SubstringRepetitionUnitTest {

    private String validString = "aa";
    private String validStringTwo = "ababab";
    private String validStringThree = "baeldungbaeldung";

    private String invalidString = "aca";
    private String invalidStringTwo = "ababa";
    private String invalidStringThree = "baeldungnonrepeatedbaeldung";

    @Test
    public void givenValidStrings_whenCheckIfContainsOnlySubstrings_thenReturnsTrue() {
        assertTrue(containsOnlySubstrings(validString));
        assertTrue(containsOnlySubstrings(validStringTwo));
        assertTrue(containsOnlySubstrings(validStringThree));
    }

    @Test
    public void givenInvalidStrings_whenCheckIfContainsOnlySubstrings_thenReturnsFalse() {
        assertFalse(containsOnlySubstrings(invalidString));
        assertFalse(containsOnlySubstrings(invalidStringTwo));
        assertFalse(containsOnlySubstrings(invalidStringThree));
    }

    @Test
    public void givenValidStrings_whenCheckEfficientlyIfContainsOnlySubstrings_thenReturnsTrue() {
        assertTrue(containsOnlySubstringsEfficient(validString));
        assertTrue(containsOnlySubstringsEfficient(validStringTwo));
        assertTrue(containsOnlySubstringsEfficient(validStringThree));
    }

    @Test
    public void givenInvalidStrings_whenCheckEfficientlyIfContainsOnlySubstrings_thenReturnsFalse() {
        assertFalse(containsOnlySubstringsEfficient(invalidString));
        assertFalse(containsOnlySubstringsEfficient(invalidStringTwo));
        assertFalse(containsOnlySubstringsEfficient(invalidStringThree));
    }
}
