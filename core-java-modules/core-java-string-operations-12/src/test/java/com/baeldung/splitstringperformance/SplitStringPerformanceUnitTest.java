package com.baeldung.splitstringperformance;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class SplitStringPerformanceUnitTest {

    private static final String TEXT = "apple,banana,grape";
    private static final String[] EXPECTED = {"apple", "banana", "grape"};

    @Test
    void givenString_whenUsingStringSplit_thenCorrectlySplits() {
        String[] parts = TEXT.split(",");
        assertArrayEquals(EXPECTED, parts);
    }

    @Test
    void givenString_whenUsingPatternSplit_thenCorrectlySplits() {
        Pattern comma = Pattern.compile(",");
        String[] parts = comma.split(TEXT);
        assertArrayEquals(EXPECTED, parts);
    }

    @Test
    void givenString_whenUsingManualSplit_thenCorrectlySplits() {
        List<String> tokens = new ArrayList<>();
        int start = 0, idx;
        while ((idx = TEXT.indexOf(",", start)) >= 0) {
            tokens.add(TEXT.substring(start, idx));
            start = idx + 1;
        }
        tokens.add(TEXT.substring(start));

        assertArrayEquals(EXPECTED, tokens.toArray(new String[0]));
    }

    @Test
    void givenStringWithExtraDelimiter_whenSplitting_thenHandlesEmptyTokens() {
        String input = "apple,,banana,";

        // Use limit = -1 to preserve trailing empty tokens
        String[] stringSplit = input.split(",", -1);
        String[] patternSplit = Pattern.compile(",").split(input, -1);

        assertArrayEquals(new String[]{"apple", "", "banana", ""}, stringSplit);
        assertArrayEquals(new String[]{"apple", "", "banana", ""}, patternSplit);
    }

    @Test
    void givenStringWithWhitespace_whenSplittingWithRegex_thenTrimsCorrectly() {
        String input = "apple   banana\tgrape";
        String[] parts = input.split("\\s+");

        assertArrayEquals(new String[]{"apple", "banana", "grape"}, parts);
    }
}
