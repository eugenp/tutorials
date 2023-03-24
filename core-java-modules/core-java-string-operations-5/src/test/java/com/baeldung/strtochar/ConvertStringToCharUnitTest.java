package com.baeldung.strtochar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConvertStringToCharUnitTest {
    private static final String STRING_b = "b";
    private static final String STRING_Baeldung = "Baeldung";

    @Test
    void givenStringWithSingleChar_whenCallingCharAt_thenGetExpectedResult() {
        assertEquals('b', STRING_b.charAt(0));
        assertEquals('l', STRING_Baeldung.charAt(3));
        assertThrows(StringIndexOutOfBoundsException.class, () -> "".charAt(0));
    }

    @Test
    void givenStringWithMultipleChars_whenCallingCharAt_thenGetExpectedResult() {
        assertArrayEquals(new char[] { 'B', 'a', 'e', 'l', 'd', 'u', 'n', 'g' }, STRING_Baeldung.toCharArray());
        assertArrayEquals(new char[] {}, "".toCharArray());
    }

    @Test
    void givenStringWithMultipleChars_whenCallingGetChars_thenGetExpectedResult() {
        char[] aeld = new char[4];
        STRING_Baeldung.getChars(1, 5, aeld, 0);
        assertArrayEquals(new char[] { 'a', 'e', 'l', 'd' }, aeld);

        char[] anotherArray = new char[] { '#', '#', '#', '#', '#', '#' };
        STRING_Baeldung.getChars(1, 5, anotherArray, 1);
        assertArrayEquals(new char[] { '#', 'a', 'e', 'l', 'd', '#' }, anotherArray);
    }
}