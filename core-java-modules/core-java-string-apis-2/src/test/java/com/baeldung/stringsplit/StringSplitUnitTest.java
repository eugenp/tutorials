package com.baeldung.stringsplit;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.Test;

public class StringSplitUnitTest {

    @Test
    void whenSplit_thenCorrect() {
        String s = "Welcome to Baeldung";
        String[] expected1 = new String[] { "Welcome", "to", "Baeldung" };
        String[] expected2 = new String[] { "Welcome", "to Baeldung" };
        assertArrayEquals(expected1, s.split(" "));
        assertArrayEquals(expected2, s.split(" ", 2));
    }

    @Test
    void whenSplitEmptyString_thenGetExpectedArray() {
        String s = "";
        String[] expected = new String[] { "" };
        String[] result = s.split(",");
        assertArrayEquals(expected, result);
    }

    @Test
    void whenSplitWithDotDelimiter_thenGetExpectedArray() {
        String s = "www.example.com";
        String[] expected = new String[] { "www", "example", "com" };
        String[] result = s.split("\\.");
        assertArrayEquals(expected, result);
    }

    @Test
    void whenSplitWithCommaDelimiter_thenHandleDifferentEncodings() {
        String utf8String = "Hello, 你好, Bonjour";
        String[] expected = new String[] { "Hello, ", "好, Bonjour" };
        String[] result = utf8String.split("你");
        assertArrayEquals(expected, result);
    }

    @Test
    void whenSplitWithMultipleDelimiters_thenGetExpectedArray() {
        String s = "apple,banana;orange|grape";
        String[] expected = new String[] { "apple", "banana", "orange", "grape" };
        String[] result = s.split("[,;|]");
        assertArrayEquals(expected, result);
    }

    @Test
    void whenSplitWithLeadingAndTrailingCommas_thenGetExpectedArray() {
        String s = ",apple,banana,";
        String[] expected = new String[] { "", "apple", "banana" };
        String[] result = s.split(",");
        assertArrayEquals(expected, result);

        String[] expectedWithLimit = new String[] { "", "apple", "banana", "" };
        String[] resultWithLimit = s.split(",", -1);
        assertArrayEquals(expectedWithLimit, resultWithLimit);
    }

    @Test
    void whenPassInvalidParameterToSplit_thenPatternSyntaxExceptionThrown() {
        String s = "Welcome*to Baeldung";

        assertThrows(PatternSyntaxException.class, () -> {
            String[] result = s.split("*");
        });
    }
}
