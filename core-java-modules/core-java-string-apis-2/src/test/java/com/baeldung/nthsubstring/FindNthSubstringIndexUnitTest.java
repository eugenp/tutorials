package com.baeldung.nthsubstring;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindNthSubstringIndexUnitTest {
    //                                  "0       8       16      24    "
    private final static String INPUT = "a word, a word, a word, a word";

    @Test
    void whenCallingIndexOfTwice_thenGetTheSecondSubstringIndex() {
        int firstIdx = INPUT.indexOf("a");
        int result = INPUT.indexOf("a", firstIdx + "a".length());
        assertEquals(8, result);
    }

    // the recursive approach
    static int nthIndexOf(String input, String substring, int nth) {
        if (nth == 1) {
            return input.indexOf(substring);
        } else {
            return input.indexOf(substring, nthIndexOf(input, substring, nth - 1) + substring.length());
        }
    }

    @Test
    void whenCallingRecursiveMethod_thenGetTheExpectedResult() {
        int result1 = nthIndexOf(INPUT, "a", 1);
        assertEquals(0, result1);

        int result2 = nthIndexOf(INPUT, "a", 2);
        assertEquals(8, result2);

        int result3 = nthIndexOf(INPUT, "a", 3);
        assertEquals(16, result3);

        int result4 = nthIndexOf(INPUT, "a", 4);
        assertEquals(24, result4);

        int result5 = nthIndexOf(INPUT, "a", 5);
        assertEquals(-1, result5);
    }

    // loop-based approach
    static int nthIndexOf2(String input, String substring, int nth) {
        int index = -1;
        while (nth > 0) {
            index = input.indexOf(substring, index + substring.length());
            if (index == -1) {
                return -1;
            }
            nth--;
        }
        return index;
    }

    @Test
    void whenCallingLoopBasedMethod_thenGetTheExpectedResult() {
        int result1 = nthIndexOf2(INPUT, "a", 1);
        assertEquals(0, result1);

        int result2 = nthIndexOf2(INPUT, "a", 2);
        assertEquals(8, result2);

        int result3 = nthIndexOf2(INPUT, "a", 3);
        assertEquals(16, result3);

        int result4 = nthIndexOf2(INPUT, "a", 4);
        assertEquals(24, result4);

        int result5 = nthIndexOf2(INPUT, "a", 5);
        assertEquals(-1, result5);
    }

    static int nthOccurrenceIndex(String input, String regexPattern, int nth) {
        Matcher matcher = Pattern.compile(regexPattern).matcher(INPUT);
        for (int i = 0; i < nth; i++) {
            if (!matcher.find()) {
                return -1;
            }
        }
        return matcher.start();
    }

    @Test
    void whenCallingRegexBasedMethod_thenGetTheExpectedResult() {
        int result1 = nthOccurrenceIndex(INPUT, "a", 1);
        assertEquals(0, result1);

        int result2 = nthOccurrenceIndex(INPUT, "a", 2);
        assertEquals(8, result2);

        int result3 = nthOccurrenceIndex(INPUT, "a", 3);
        assertEquals(16, result3);

        int result4 = nthOccurrenceIndex(INPUT, "a", 4);
        assertEquals(24, result4);

        int result5 = nthOccurrenceIndex(INPUT, "a", 5);
        assertEquals(-1, result5);
    }
}