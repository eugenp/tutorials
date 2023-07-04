package com.baeldung.digitsandnondigits;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.base.Stopwatch;

public class SplitDigitsAndNondigitsUnitTest {
    private static final String INPUT1 = "01Michael Jackson23Michael Jordan42Michael Bolton999Michael Johnson000";
    private static final String[] EXPECTED1 = new String[] { "01", "Michael Jackson", "23", "Michael Jordan", "42", "Michael Bolton", "999", "Michael Johnson", "000" };
    private static final List<String> EXPECTED_LIST1 = Arrays.asList(EXPECTED1);

    private static final String INPUT2 = "Michael Jackson01Michael Jordan23Michael Bolton42Michael Johnson999Great Michaels";
    private static final String[] EXPECTED2 = new String[] { "Michael Jackson", "01", "Michael Jordan", "23", "Michael Bolton", "42", "Michael Johnson", "999", "Great Michaels" };
    private static final List<String> EXPECTED_LIST2 = Arrays.asList(EXPECTED2);

    @Test
    void whenUsingLookaroundRegex_thenGetExpectedResult() {
        String splitRE = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
        String[] result1 = INPUT1.split(splitRE);
        assertArrayEquals(EXPECTED1, result1);

        String[] result2 = INPUT2.split(splitRE);
        assertArrayEquals(EXPECTED2, result2);
    }

    static List<String> parseString(String input) {
        List<String> result = new ArrayList<>();
        int start = 0;
        int state = 0; // 0: init; 1: digit; 2: non-digit
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                if (state == 2) { // non-digit to digit
                    result.add(input.substring(start, i));
                    start = i;
                }
                state = 1;
            } else {
                if (state == 1) {
                    result.add(input.substring(start, i));
                    start = i;
                }
                state = 2;
            }
        }
        result.add(input.substring(start));
        return result;
    }

    @Test
    void whenCheckEachChar_thenGetExpectedResult() {
        List<String> result1 = parseString(INPUT1);
        assertEquals(EXPECTED_LIST1, result1);

        List<String> result2 = parseString(INPUT2);
        assertEquals(EXPECTED_LIST2, result2);
    }
}