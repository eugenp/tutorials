package com.baeldung;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class CharArrayToStringUnitTest {

    @Test
    public void givenACharArray_whenPassingItInStringConstructor_thenShouldGetStringWithSameCharSequence() {
        char[] charArray = { 'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!' };
        String sampleString = new String(charArray);

        assertEquals("Hello, world!", sampleString);
    }

    @Test
    public void givenACharArray_whenPassingItInConstructorWithOffsetAndCount_thenShouldGetStringWithCorrectCharSequence() {
        char[] charArray = { 'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!' };
        String sampleString = new String(charArray, 7, 6);

        assertEquals("world!", sampleString);
    }

    @Test
    public void givenACharArray_whenPassingItInStringFactoryMethod_thenShouldGetAStringWithSameCharSequence() {
        char[] charArray = { 'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!' };
        String sampleString1 = String.valueOf(charArray);
        String sampleString2 = String.copyValueOf(charArray);

        assertEquals("Hello, world!", sampleString1);
        assertEquals("Hello, world!", sampleString2);
    }

    @Test
    public void givenACharArray_whenPassingItInFactoryMethodWithOffsetAndCound_thenShouldGetStringWithCorrectCharSequence() {
        char[] charArray = { 'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!' };
        String sampleString1 = String.valueOf(charArray, 7, 6);
        String sampleString2 = String.copyValueOf(charArray, 7, 6);

        assertEquals("world!", sampleString1);
        assertEquals("world!", sampleString2);
    }

    @Test
    public void givenAString_whenConvertingItToACharArray_thenShouldGetAnArrayWithTheSameCharSequence() {
        String sampleString7 = "Hello, world!";
        char[] charArray = sampleString7.toCharArray();
        char[] expected = { 'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!' };

        assertArrayEquals(expected, charArray);
    }

    @Test
    public void givenAString_whenConvertingAPartToCharArray_thenShouldGetCharArrayWithCorrectCharSequence() {
        String sampleString = "Hello, world!";
        char[] charArray = new char[6];
        sampleString.getChars(7, sampleString.length(), charArray, 0);
        char[] expected = { 'w', 'o', 'r', 'l', 'd', '!' };

        assertArrayEquals(expected, charArray);
    }

}
