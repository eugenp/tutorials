package com.baeldung.string;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class RemoveLastCharTest {

    public static final String TEST_STRING = "abcdef";
    public static final String NULL_STRING = null;
    public static final String EMPTY_STRING = "";
    public static final String ONE_CHAR_STRING = "a";
    public static final String WHITE_SPACE_AT_THE_END_STRING = "abc ";

    @Test
    public void givenTestString_whenSubstring_thenGetStingWithoutLastChar() {
        assertEquals("abcde", RemoveLastChar.substring(TEST_STRING));
        assertEquals("abcde", StringUtils.substring(TEST_STRING, 0, TEST_STRING.length() - 1));
        assertEquals("abcde", StringUtils.chop(TEST_STRING));
        assertEquals("abcde", TEST_STRING.replaceAll(".$", ""));
        assertEquals("abcde", RemoveLastChar.chop(TEST_STRING));
    }

    @Test
    public void givenNullString_whenSubstring_thenGetNullString() {
        assertEquals(NULL_STRING, RemoveLastChar.substring(NULL_STRING));
        assertEquals(NULL_STRING, StringUtils.chop(NULL_STRING));
        assertEquals(NULL_STRING, RemoveLastChar.chop(NULL_STRING));
    }

    @Test
    public void givenEmptyString_whenSubstring_thenGetEmptyString() {
        assertEquals(EMPTY_STRING, RemoveLastChar.substring(EMPTY_STRING));
        assertEquals(EMPTY_STRING, StringUtils.substring(EMPTY_STRING, 0, EMPTY_STRING.length() - 1));
        assertEquals(EMPTY_STRING, StringUtils.chop(EMPTY_STRING));
        assertEquals(EMPTY_STRING, EMPTY_STRING.replaceAll(".$", ""));
        assertEquals(EMPTY_STRING, RemoveLastChar.chop(EMPTY_STRING));
    }

    @Test
    public void givenOneCharString_whenSubstring_thenGetEmptyString() {
        assertEquals(EMPTY_STRING, RemoveLastChar.substring(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, StringUtils.substring(ONE_CHAR_STRING, 0, ONE_CHAR_STRING.length() - 1));
        assertEquals(EMPTY_STRING, StringUtils.chop(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, ONE_CHAR_STRING.replaceAll(".$", ""));
        assertEquals(EMPTY_STRING, RemoveLastChar.chop(ONE_CHAR_STRING));
    }

    @Test
    public void givenStringWithWhiteSpaceAtTheEnd_whenSubstring_thenGetStringWithoutWhiteSpaceAtTheEnd() {
        assertEquals("abc", RemoveLastChar.substring(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", StringUtils.substring(WHITE_SPACE_AT_THE_END_STRING, 0, WHITE_SPACE_AT_THE_END_STRING.length() - 1));
        assertEquals("abc", StringUtils.chop(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", WHITE_SPACE_AT_THE_END_STRING.replaceAll(".$", ""));
        assertEquals("abc", RemoveLastChar.chop(WHITE_SPACE_AT_THE_END_STRING));
    }
}
