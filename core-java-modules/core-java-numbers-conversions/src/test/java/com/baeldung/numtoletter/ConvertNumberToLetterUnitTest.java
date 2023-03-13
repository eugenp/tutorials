package com.baeldung.numtoletter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConvertNumberToLetterUnitTest {

    static String numToLetterBySubstr(int i) {
        String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (i < 0 || i > 25) {
            return "?";
        } else {
            return LETTERS.substring(i, i + 1);
        }
    }

    static char numToLetterByAsciiCode(int i) {
        if (i < 0 || i > 25) {
            return '?';
        } else {
            return (char) ('A' + i);
        }
    }

    @Test
    void givenANumber_whenConvertToLetterUsingSubstring_shouldGetExpectedResult() {
        String negativeInputResult = numToLetterBySubstr(-7);
        assertEquals("?", negativeInputResult);

        String tooLargeInputResult = numToLetterBySubstr(42);
        assertEquals("?", tooLargeInputResult);

        String result = numToLetterBySubstr(10);
        assertEquals("K", result);
    }

    @Test
    void givenANumber_whenConvertToLetterUsingAscii_shouldGetExpectedResult() {
        char negativeInputResult = numToLetterByAsciiCode(-7);
        assertEquals('?', negativeInputResult);

        char tooLargeInputResult = numToLetterByAsciiCode(42);
        assertEquals('?', tooLargeInputResult);

        char charResult = numToLetterByAsciiCode(10);
        assertEquals('K', charResult);

        assertEquals("K", String.valueOf(charResult));
    }
}