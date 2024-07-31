package com.baeldung.string.shiftchar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ShiftCharInStringUnitTest {

    private final static String STRING = "abcdefg";

    private final static String EXPECT_1X = "gabcdef";
    private final static String EXPECT_2X = "fgabcde";
    private final static String EXPECT_3X = "efgabcd";
    private final static String EXPECT_6X = "bcdefga";
    private final static String EXPECT_7X = "abcdefg";
    private final static String EXPECT_24X = "efgabcd";

    private final static String B_EXPECT_1X = "bcdefga";
    private final static String B_EXPECT_2X = "cdefgab";
    private final static String B_EXPECT_3X = "defgabc";
    private final static String B_EXPECT_6X = "gabcdef";
    private final static String B_EXPECT_7X = "abcdefg";
    private final static String B_EXPECT_24X = "defgabc";

    static String rotateString1(String s, int c, boolean forward) {
        if (c < 0) {
            throw new IllegalArgumentException("Rotation character count cannot be negative!");
        }
        int len = s.length();
        int n = c % len;
        if (n == 0) {
            return s;
        }
        n = forward ? n : len - n;
        return s.substring(len - n, len) + s.substring(0, len - n);
    }

    static String rotateString2(String s, int c, boolean forward) {
        if (c < 0) {
            throw new IllegalArgumentException("Rotation character count cannot be negative!");
        }
        int len = s.length();
        int n = c % len;
        if (n == 0) {
            return s;
        }
        String ss = s + s;
        n = forward ? n : len - n;
        return ss.substring(len - n, 2 * len - n);
    }

    static boolean rotatedFrom(String rotated, String rotateFrom) {
        return rotateFrom.length() == rotated.length() && (rotateFrom + rotateFrom).contains(rotated);
    }

    @Test
    void whenUsingRotateString1_thenGetExpectedResults() {
        assertEquals(EXPECT_1X, rotateString1(STRING, 1, true));
        assertEquals(EXPECT_2X, rotateString1(STRING, 2, true));
        assertEquals(EXPECT_3X, rotateString1(STRING, 3, true));
        assertEquals(EXPECT_6X, rotateString1(STRING, 6, true));
        assertEquals(EXPECT_7X, rotateString1(STRING, 7, true));
        assertEquals(EXPECT_24X, rotateString1(STRING, 24, true));

        //backward
        assertEquals(B_EXPECT_1X, rotateString1(STRING, 1, false));
        assertEquals(B_EXPECT_2X, rotateString1(STRING, 2, false));
        assertEquals(B_EXPECT_3X, rotateString1(STRING, 3, false));
        assertEquals(B_EXPECT_6X, rotateString1(STRING, 6, false));
        assertEquals(B_EXPECT_7X, rotateString1(STRING, 7, false));
        assertEquals(B_EXPECT_24X, rotateString1(STRING, 24, false));
    }

    @Test
    void whenUsingShiftString2_thenGetExpectedResults() {
        assertEquals(EXPECT_1X, rotateString2(STRING, 1, true));
        assertEquals(EXPECT_2X, rotateString2(STRING, 2, true));
        assertEquals(EXPECT_3X, rotateString2(STRING, 3, true));
        assertEquals(EXPECT_6X, rotateString2(STRING, 6, true));
        assertEquals(EXPECT_7X, rotateString2(STRING, 7, true));
        assertEquals(EXPECT_24X, rotateString2(STRING, 24, true));

        //backward
        assertEquals(B_EXPECT_1X, rotateString2(STRING, 1, false));
        assertEquals(B_EXPECT_2X, rotateString2(STRING, 2, false));
        assertEquals(B_EXPECT_3X, rotateString2(STRING, 3, false));
        assertEquals(B_EXPECT_6X, rotateString2(STRING, 6, false));
        assertEquals(B_EXPECT_7X, rotateString2(STRING, 7, false));
        assertEquals(B_EXPECT_24X, rotateString2(STRING, 24, false));
    }

    @Test
    void whenUsingRotateFrom_thenGetExpectedResults() {
        assertTrue(rotatedFrom(EXPECT_7X, STRING));
        assertTrue(rotatedFrom(B_EXPECT_3X, STRING));
        assertFalse(rotatedFrom("abcefgd", STRING));
    }
}