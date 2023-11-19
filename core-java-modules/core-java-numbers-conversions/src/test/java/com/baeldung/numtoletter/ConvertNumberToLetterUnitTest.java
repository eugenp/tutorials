package com.baeldung.numtoletter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConvertNumberToLetterUnitTest {

    static char numToLetterBySubstr(int i) {
        String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (i > 0 && i <= 25) {
            return LETTERS.substring(i, i + 1).charAt(0);
        } else {
            return '?';
        }
    }

    static char numToLetterByAsciiCode(int i) {
        if (i > 0 && i <= 25) {
            return (char) ('A' + i);
        } else {
            return '?';
        }
    }

    @Test
    void givenANumber_whenConvertToLetterUsingSubstring_shouldGetExpectedResult() {
        char negativeInputResult = numToLetterBySubstr(-7);
        assertEquals('?', negativeInputResult);

        char tooLargeInputResult = numToLetterBySubstr(42);
        assertEquals('?', tooLargeInputResult);

        char result = numToLetterBySubstr(10);
        assertEquals('K', result);
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