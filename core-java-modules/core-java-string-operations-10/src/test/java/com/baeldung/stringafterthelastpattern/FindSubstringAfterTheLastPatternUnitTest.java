package com.baeldung.stringafterthelastpattern;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class FindSubstringAfterTheLastPatternUnitTest {

    private static final String INPUT1 = "a,   b,   c,   I need this value";
    private static final String EXPECTED1 = "I need this value";

    private static final String INPUT2 = "no-pattern-found";
    private static final String EXPECTED2 = "";

    private static final String INPUT3 = "a,   b,   c,   ";
    private static final String EXPECTED3 = "";

    public String afterTheLastPatternBySubstring(String input, String pattern) {
        int index = input.lastIndexOf(pattern);
        return index >= 0 ? input.substring(index + pattern.length()) : "";
    }

    @Test
    void whenUsingSubstring_thenCorrect() {
        String pattern = ",   ";

        String result1 = afterTheLastPatternBySubstring(INPUT1, pattern);
        assertEquals(EXPECTED1, result1);

        String result2 = afterTheLastPatternBySubstring(INPUT2, pattern);
        assertEquals(EXPECTED2, result2);

        String result3 = afterTheLastPatternBySubstring(INPUT3, pattern);
        assertEquals(EXPECTED3, result3);
    }

    public String afterTheLastPatternBySplit(String input, String pattern) {
        String[] arr = input.split(pattern, -1);
        return (arr.length >= 2) ? arr[arr.length - 1] : "";
    }

    @Test
    void whenUsingSplitWithOrWithoutLimit_thenGetDifferentResult() {
        String pattern = ", {3}";

        String[] array1 = INPUT3.split(pattern);
        assertArrayEquals(new String[] { "a", "b", "c" }, array1);

        String[] array2 = INPUT3.split(pattern, -1);
        assertArrayEquals(new String[] { "a", "b", "c", "" }, array2);
    }

    @Test
    void whenUsingSplit_thenCorrect() {
        String pattern = ", {3}";

        String result1 = afterTheLastPatternBySplit(INPUT1, pattern);
        assertEquals(EXPECTED1, result1);

        String result2 = afterTheLastPatternBySplit(INPUT2, pattern);
        assertEquals(EXPECTED2, result2);

        String result3 = afterTheLastPatternBySplit(INPUT3, pattern);
        assertEquals(EXPECTED3, result3);
    }

    public String afterTheLastPatternByReplaceAll(String input, String pattern) {
        String result = input.replaceAll(pattern, "");
        return result.equals(input) ? "" : result;
    }

    @Test
    void whenUsingReplaceAll_thenCorrect() {
        String pattern = ".*, {3}";

        String result1 = afterTheLastPatternByReplaceAll(INPUT1, pattern);
        assertEquals(EXPECTED1, result1);

        String result2 = afterTheLastPatternByReplaceAll(INPUT2, pattern);
        assertEquals(EXPECTED2, result2);

        String result3 = afterTheLastPatternByReplaceAll(INPUT3, pattern);
        assertEquals(EXPECTED3, result3);
    }
}