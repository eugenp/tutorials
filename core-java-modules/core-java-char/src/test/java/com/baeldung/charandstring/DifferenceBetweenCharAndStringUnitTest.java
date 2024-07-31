package com.baeldung.charandstring;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

public class DifferenceBetweenCharAndStringUnitTest {

    @Test
    void whenPlusTwoChars_thenGetSumAsInteger() {
        char h = 'H'; // the value is 72
        char i = 'i'; // the value is 105
        assertEquals(177, h + i);
        assertInstanceOf(Integer.class, h + i);
    }

    @Test
    void whenPlusTwoStrings_thenConcatenateThem() {
        String i = "i";
        String h = "H";
        assertEquals("Hi", h + i);
    }

    @Test
    void whenPlusCharsAndStrings_thenGetExpectedValues() {
        char c = 'C';
        assertEquals("C", "" + c);

        char h = 'H'; // the value is 72
        char i = 'i'; // the value is 105
        assertEquals("Hi", "" + h + i);
        assertEquals("Hi", h + "" + i);
        assertEquals("177", h + i + "");
    }

    @Test
    void whenStringChars_thenGetCharArray() {
        char h = 'h';
        char e = 'e';
        char l = 'l';
        char o = 'o';

        String hello = "hello";
        assertEquals(h, hello.charAt(0));
        assertEquals(e, hello.charAt(1));
        assertEquals(l, hello.charAt(2));
        assertEquals(l, hello.charAt(3));
        assertEquals(o, hello.charAt(4));

        char[] chars = new char[] { h, e, l, l, o };
        char[] charsFromString = hello.toCharArray();
        assertArrayEquals(chars, charsFromString);
    }
}