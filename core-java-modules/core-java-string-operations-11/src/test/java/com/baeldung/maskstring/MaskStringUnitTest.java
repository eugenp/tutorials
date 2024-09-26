package com.baeldung.maskstring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class MaskStringUnitTest {

    private static final String INPUT_1 = "a b c d 1234";
    private static final String INPUT_2 = "a b c d     ";
    private static final String INPUT_3 = "a b";

    private static final String EXPECTED_1 = "********1234";
    private static final String EXPECTED_2 = "********    ";
    private static final String EXPECTED_3 = "a b";

    public String maskByCharArray(String input) {
        if (input.length() <= 4) {
            return input;
        }
        char[] chars = input.toCharArray();
        Arrays.fill(chars, 0, chars.length - 4, '*');
        return new String(chars);
    }

    @Test
    void whenUsingCharArray_thenCorrect() {
        assertEquals(EXPECTED_1, maskByCharArray(INPUT_1));
        assertEquals(EXPECTED_2, maskByCharArray(INPUT_2));
        assertEquals(EXPECTED_3, maskByCharArray(INPUT_3));
    }

    public String maskBySubstring(String input) {
        if (input.length() <= 4) {
            return input;
        }
        String toMask = input.substring(0, input.length() - 4);
        String keepPlain = input.substring(input.length() - 4);
        return toMask.replaceAll(".", "*") + keepPlain;
    }

    @Test
    void whenUsingSubstring_thenCorrect() {
        assertEquals(EXPECTED_1, maskBySubstring(INPUT_1));
        assertEquals(EXPECTED_2, maskBySubstring(INPUT_2));
        assertEquals(EXPECTED_3, maskBySubstring(INPUT_3));
    }

    public String maskByRegex(String input) {
        if (input.length() <= 4) {
            return input;
        }
        return input.replaceAll(".(?=.{4})", "*");
    }

    @Test
    void whenUsingRegex_thenCorrect() {
        assertEquals(EXPECTED_1, maskByRegex(INPUT_1));
        assertEquals(EXPECTED_2, maskByRegex(INPUT_2));
        assertEquals(EXPECTED_3, maskByRegex(INPUT_3));
    }

    public String maskByRepeat(String input) {
        if (input.length() <= 4) {
            return input;
        }
        int maskLen = input.length() - 4;
        return "*".repeat(maskLen) + input.substring(maskLen);
    }

    @Test
    void whenUsingRepeat_thenCorrect() {
        assertEquals(EXPECTED_1, maskByRepeat(INPUT_1));
        assertEquals(EXPECTED_2, maskByRepeat(INPUT_2));
        assertEquals(EXPECTED_3, maskByRepeat(INPUT_3));
    }
}