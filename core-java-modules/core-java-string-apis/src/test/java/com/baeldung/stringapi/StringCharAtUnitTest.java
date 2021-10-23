package com.baeldung.stringapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCharAtUnitTest {
    @Test
    public void whenCallCharAt_thenSuccess() {
        String sample = "abcdefg";
        assertEquals('d', sample.charAt(3));
    }

    @Test()
    public void whenCharAtNonExist_thenIndexOutOfBoundsExceptionThrown() {
        String sample = "abcdefg";
        assertThrows(IndexOutOfBoundsException.class, () -> sample.charAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> sample.charAt(sample.length()));
    }

    @Test
    public void whenCallCharAt_thenReturnString() {
        String sample = "abcdefg";
        assertEquals("a", Character.toString(sample.charAt(0)));
        assertEquals("a", String.valueOf(sample.charAt(0)));
    }

}