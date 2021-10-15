package com.baeldung.stringapi;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCharAtUnitTest {
    @Test
    public void whenCallCharAt_thenCorrect() {
        String sample = "abcdefg";
        assertEquals('d', sample.charAt(3));
    }

    @Test
    public void whenLoopCharAt_thenCorrect() {
        String sample = "AbCdEfG";
        int count = 0;
        for (int i = 0; i < sample.length(); i++) {
            if (Character.isUpperCase(sample.charAt(i))) {
                count++;
            }
        }
        Assert.assertEquals(4, count);
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
        assertEquals("a", sample.charAt(0) + "");
        assertEquals("a", Character.toString(sample.charAt(0)));
        assertEquals("a", String.valueOf(sample.charAt(0)));
    }

}