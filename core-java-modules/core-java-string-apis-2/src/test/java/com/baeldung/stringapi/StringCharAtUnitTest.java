package com.baeldung.stringapi;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringCharAtUnitTest {

    @Test
    public void whenCallCharAt_thenSuccess() {
        String sample = "abcdefg";
        assertEquals('d', sample.charAt(3));
    }

    @Test()
    public void whenCharAtNonExist_thenIndexOutOfBoundsExceptionThrown() {
        String sample = "abcdefg";
        assertThrows(StringIndexOutOfBoundsException.class, () -> sample.charAt(-1));
        assertThrows(StringIndexOutOfBoundsException.class, () -> sample.charAt(sample.length()));
    }

    @Test
    public void whenUsingToCharArrayThenIndexing_thenCorrect() {
        String sample = "abcdefg";
        char[] chars = sample.toCharArray();
        assertEquals('d', chars[3]);
        assertEquals('f', chars[5]);
    }

    @Test
    public void whenUsingCodePointAt_thenCorrect() {
        String sample = "abcdefg";
        assertEquals('d', sample.codePointAt(3));
        assertEquals('f', sample.codePointAt(5));

        String emojiString = "😊"; // '😊' is Unicode char: U+1F60A
        assertEquals(0x1F60A, emojiString.codePointAt(0));
    }

    @Test
    public void whenUsingGetChars_thenCorrect() {
        String sample = "abcdefg";
        char[] singleTargetChar = new char[1];
        sample.getChars(3, 3 + 1, singleTargetChar, 0);
        assertEquals('d', singleTargetChar[0]);

        char[] multiTargetChars = new char[3];
        sample.getChars(3, 3 + 3, multiTargetChars, 0);
        assertArrayEquals(new char[] { 'd', 'e', 'f' }, multiTargetChars);
    }

    char getCharUsingStream(String str, int index) {
        return str.chars()
            .mapToObj(ch -> (char) ch)
            .toArray(Character[]::new)[index];
    }

    @Test
    public void whenUsingStreamAPI_thenCorrect() {
        String sample = "abcdefg";
        assertEquals('d', getCharUsingStream(sample, 3));
        assertEquals('f', getCharUsingStream(sample, 5));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> getCharUsingStream(sample, 100));
    }

    @Test
    public void whenCallCharAt_thenReturnString() {
        String sample = "abcdefg";
        assertEquals("d", Character.toString(sample.charAt(3)));
        assertEquals("d", String.valueOf(sample.charAt(3)));
    }

}