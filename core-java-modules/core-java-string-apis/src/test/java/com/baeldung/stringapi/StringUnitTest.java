package com.baeldung.stringapi;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.IllegalFormatException;
import java.util.regex.PatternSyntaxException;

import static org.junit.Assert.*;

public class StringUnitTest {

    @Test
    public void whenCallCodePointAt_thenDecimalUnicodeReturned() {
        assertEquals(97, "abcd".codePointAt(0));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void whenPassNonExistingIndex_thenExceptionThrown() {
        int a = "abcd".codePointAt(4);
    }

    @Test
    public void whenCallConcat_thenCorrect() {
        assertEquals("elephant", "elep".concat("hant"));
    }

    @Test
    public void whenGetBytes_thenCorrect() throws UnsupportedEncodingException {
        byte[] byteArray1 = "abcd".getBytes();
        byte[] byteArray2 = "efgh".getBytes(StandardCharsets.US_ASCII);
        byte[] byteArray3 = "ijkl".getBytes("UTF-8");
        byte[] expected1 = new byte[] { 97, 98, 99, 100 };
        byte[] expected2 = new byte[] { 101, 102, 103, 104 };
        byte[] expected3 = new byte[] { 105, 106, 107, 108 };

        assertArrayEquals(expected1, byteArray1);
        assertArrayEquals(expected2, byteArray2);
        assertArrayEquals(expected3, byteArray3);
    }

    @Test
    public void whenGetBytesUsingASCII_thenCorrect() {
        byte[] byteArray = "efgh".getBytes(StandardCharsets.US_ASCII);
        byte[] expected = new byte[] { 101, 102, 103, 104 };

        assertArrayEquals(expected, byteArray);
    }

    @Test
    public void whenCreateStringUsingByteArray_thenCorrect() {
        byte[] array = new byte[] { 97, 98, 99, 100 };
        String s = new String(array);

        assertEquals("abcd", s);
    }

    @Test
    public void whenCallCharAt_thenCorrect() {
        assertEquals('P', "Paul".charAt(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenCharAtOnNonExistingIndex_thenIndexOutOfBoundsExceptionThrown() {
        char character = "Paul".charAt(4);
    }

    @Test
    public void whenCallCodePointCount_thenCorrect() {
        assertEquals(2, "abcd".codePointCount(0, 2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenSecondIndexEqualToLengthOfString_thenIndexOutOfBoundsExceptionThrown() {
        char character = "Paul".charAt(4);
    }

    @Test
    public void whenCallContains_thenCorrect() {
        String s = "abcd";

        assertTrue(s.contains("abc"));
        assertFalse(s.contains("cde"));
    }

    @Test
    public void whenCallCopyValueOf_thenStringConstructed() {
        char[] array = new char[] { 'a', 'b', 'c', 'd' };

        assertEquals("abcd", String.copyValueOf(array));
    }

    @Test
    public void whenCallEndsWith_thenCorrect() {
        String s1 = "test";

        assertTrue(s1.endsWith("t"));
    }

    @Test
    public void whenFormat_thenCorrect() {
        String value = "Baeldung";
        String formatted = String.format("Welcome to %s!", value);

        assertEquals("Welcome to Baeldung!", formatted);
    }

    @Test(expected = IllegalFormatException.class)
    public void whenInvalidFormatSyntax_thenIllegalFormatExceptionThrown() {
        String value = "Baeldung";
        String formatted = String.format("Welcome to %x!", value);
    }

    @Test
    public void whenCallIndexOf_thenCorrect() {
        assertEquals(1, "foo".indexOf("o"));
    }

    @Test
    public void whenCallIsEmpty_thenCorrect() {
        String s1 = "";

        assertTrue(s1.isEmpty());
    }

    @Test
    public void whenCallLastIndexOf_thenCorrect() {
        assertEquals(2, "foo".lastIndexOf("o"));
        assertEquals(2, "foo".lastIndexOf(111));
    }

    @Test
    public void whenCallRegionMatches_thenCorrect() {
        assertTrue("welcome to baeldung".regionMatches(false, 11, "baeldung", 0, 8));
    }

    @Test
    public void whenCallStartsWith_thenCorrect() {
        assertTrue("foo".startsWith("f"));
    }

    @Test
    public void whenTrim_thenCorrect() {
        assertEquals("foo", " foo  ".trim());
    }

    @Test
    public void whenSplit_thenCorrect() {
        String s = "Welcome to Baeldung";
        String[] array = new String[] { "Welcome", "to", "Baeldung" };

        assertArrayEquals(array, s.split(" "));
    }

    @Test(expected = PatternSyntaxException.class)
    public void whenPassInvalidParameterToSplit_thenPatternSyntaxExceptionThrown() {
        String s = "Welcome*to Baeldung";

        String[] result = s.split("*");
    }

    @Test
    public void whenCallSubSequence_thenCorrect() {
        String s = "Welcome to Baeldung";

        assertEquals("Welcome", s.subSequence(0, 7));
    }

    @Test
    public void whenCallSubstring_thenCorrect() {
        String s = "Welcome to Baeldung";

        assertEquals("Welcome", s.substring(0, 7));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenSecondIndexEqualToLengthOfString_thenCorrect() {
        String s = "Welcome to Baeldung";

        String sub = s.substring(0, 20);
    }

    @Test
    public void whenConvertToUpperCase_thenCorrect() {
        String s = "Welcome to Baeldung!";

        assertEquals("WELCOME TO BAELDUNG!", s.toUpperCase());
    }

    @Test
    public void whenConvertToLowerCase_thenCorrect() {
        String s = "WELCOME to BAELDUNG!";

        assertEquals("welcome to baeldung!", s.toLowerCase());
    }

    @Test
    public void whenCallReplace_thenCorrect() {
        String s = "I learn Spanish";

        assertEquals("I learn French", s.replaceAll("Spanish", "French"));
    }

    @Test
    public void whenIntern_thenCorrect() {
        String s1 = "abc";
        String s2 = new String("abc");
        String s3 = new String("foo");
        String s4 = s1.intern();
        String s5 = s2.intern();

        assertFalse(s3 == s4);
        assertTrue(s1 == s5);
    }

    @Test
    public void whenCallValueOf_thenCorrect() {
        long l = 200L;

        assertEquals("200", String.valueOf(l));
    }
}