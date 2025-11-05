package com.baeldung.stringbuildernullorempty;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringBuilderNullOrEmptyUnitTest {

    private static final StringBuilder SB_NULL = null;
    private static final StringBuilder SB_EMPTY = new StringBuilder();
    private static final StringBuilder SB_EMPTY_STR = new StringBuilder("");
    private static final StringBuilder SB_BLANK_STR = new StringBuilder("   ");
    private static final StringBuilder SB_WITH_TEXT = new StringBuilder("I am a magic string");

    public static boolean isNull(StringBuilder sb) {
        return sb == null;
    }

    @Test
    void whenCallingIsNull_thenCorrect() {
        assertTrue(isNull(SB_NULL));

        assertFalse(isNull(SB_EMPTY));
        assertFalse(isNull(SB_EMPTY_STR));
        assertFalse(isNull(SB_BLANK_STR));
        assertFalse(isNull(SB_WITH_TEXT));
    }

    public static boolean isNullOrEmptyByStrEmpty(StringBuilder sb) {
        return sb == null || sb.toString()
            .isEmpty();
    }

    @Test
    void whenCallingIsNullOrEmptyByStrEmpty_thenCorrect() {
        assertTrue(isNullOrEmptyByStrEmpty(SB_NULL));
        assertTrue(isNullOrEmptyByStrEmpty(SB_EMPTY));
        assertTrue(isNullOrEmptyByStrEmpty(SB_EMPTY_STR));

        assertFalse(isNullOrEmptyByStrEmpty(SB_BLANK_STR));
        assertFalse(isNullOrEmptyByStrEmpty(SB_WITH_TEXT));
    }

    public static boolean isNullOrEmptyByLength(StringBuilder sb) {
        return sb == null || sb.length() == 0;
    }

    @Test
    void whenCallingIsNullOrEmptyByLength_thenCorrect() {
        assertTrue(isNullOrEmptyByLength(SB_NULL));
        assertTrue(isNullOrEmptyByLength(SB_EMPTY));
        assertTrue(isNullOrEmptyByLength(SB_EMPTY_STR));

        assertFalse(isNullOrEmptyByLength(SB_BLANK_STR));
        assertFalse(isNullOrEmptyByLength(SB_WITH_TEXT));
    }

    public static boolean isNullOrEmpty(StringBuilder sb) {
        return sb == null || sb.isEmpty();
    }

    @Test
    void whenCallingIsNullOrEmpty_thenCorrect() {
        assertTrue(isNullOrEmpty(SB_NULL));
        assertTrue(isNullOrEmpty(SB_EMPTY));
        assertTrue(isNullOrEmpty(SB_EMPTY_STR));

        assertFalse(isNullOrEmpty(SB_BLANK_STR));
        assertFalse(isNullOrEmpty(SB_WITH_TEXT));
    }
}