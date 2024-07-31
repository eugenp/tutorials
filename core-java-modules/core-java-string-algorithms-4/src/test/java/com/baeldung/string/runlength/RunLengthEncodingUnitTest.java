package com.baeldung.string.runlength;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunLengthEncodingUnitTest {
    private static final String INPUT = "WWWWWWWWWWWWBAAACCDEEEEE";
    private static final String RLE = "12W1B3A2C1D5E";

    String runLengthEncode(String input) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (i + 1 < chars.length && c == chars[i + 1]) {
                count++;
            } else {
                result.append(count).append(c);
                count = 1;
            }
        }
        return result.toString();
    }


    String runLengthDecode(String rle) {
        StringBuilder result = new StringBuilder();
        char[] chars = rle.toCharArray();

        int count = 0;
        for (char c : chars) {
            if (Character.isDigit(c)) {
                count = 10 * count + Character.getNumericValue(c);
            } else {
                result.append(String.join("", Collections.nCopies(count, String.valueOf(c))));
                count = 0;
            }
        }
        return result.toString();
    }

    String runLengthEncodeByRegEx(String input) {
        String[] arr = input.split("(?<=(\\D))(?!\\1)");
        StringBuilder result = new StringBuilder();
        for (String run : arr) {
            result.append(run.length()).append(run.charAt(0));
        }
        return result.toString();
    }

    String runLengthDecodeByRegEx(String rle) {
        if (rle.isEmpty()) {
            return "";
        }
        String[] arr = rle.split("(?<=\\D)|(?=\\D+)");
        if (arr.length % 2 != 0) {
            throw new IllegalArgumentException("Not a RLE string");
        }
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= arr.length; i += 2) {
            int count = Integer.parseInt(arr[i - 1]);
            String c = arr[i];

            result.append(String.join("", Collections.nCopies(count, c)));
        }
        return result.toString();
    }

    @Test
    void whenInvokingRunLengthEncode_thenGetExpectedResult() {
        assertEquals(RLE, runLengthEncode(INPUT));
    }

    @Test
    void whenInvokingRunLengthDecode_thenGetExpectedResult() {
        assertEquals(INPUT, runLengthDecode(RLE));
    }

    @Test
    void whenInvokingRunLengthEncodeByRegEx_thenGetExpectedResult() {
        assertEquals(RLE, runLengthEncodeByRegEx(INPUT));
    }

    @Test
    void whenInvokingRunLengthDecodeByRegEx_thenGetExpectedResult() {
        assertEquals(INPUT, runLengthDecodeByRegEx(RLE));
    }
}