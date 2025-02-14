package com.baeldung.substringoperations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringSlicingUnitTest {

    @Test
    void givenString_whenExtract_thenOutputSubstring() {
        String s = "Hello, World!";
        assertEquals("Hello", s.substring(0, 5));
        assertEquals("World!", s.substring(7));
    }

    @Test
    void givenString_whenNegativeIndexing_thenOutputSubstringEquivalent() {
        String s = "Hello, World!";
        assertEquals("World!", s.substring(s.length() - 6));
    }

    @Test
    void givenString_whenStepSlicing_thenOutputSubstring() {
        String s = "Hello, World!";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i += 2) {
            result.append(s.charAt(i));
        }
        assertEquals("Hlo ol!", result.toString());
    }

    @Test
    void givenString_whenReverseString_thenOutputString() {
        String s = "Hello, World!";
        String reversed = new StringBuilder(s).reverse()
            .toString();
        assertEquals("!dlroW ,olleH", reversed);
    }
}