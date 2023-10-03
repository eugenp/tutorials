package com.baeldung.commons.convertunicode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnicodeConverterUnitTest {

    @Test
    public void whenInputHaveUnicodeSequences_ThenDecode() {
        String encodedString = "\\u0048\\u0065\\u006C\\u006C\\u006F World";
        String expectedDecodedString = "Hello World";
        assertEquals(expectedDecodedString, UnicodeConverterUtil.decodeWithApacheCommons(encodedString));
        assertEquals(expectedDecodedString, UnicodeConverterUtil.decodeWithPlainJava(encodedString));
    }

    @Test
    public void whenInputHaveNoUnicodeSequences_ThenDoNothing() {
        String inputString = "Hello World";
        assertEquals(inputString, UnicodeConverterUtil.decodeWithApacheCommons(inputString));
        assertEquals(inputString, UnicodeConverterUtil.decodeWithPlainJava(inputString));
    }

    @Test
    public void whenInputHaveUnicodeSequencesAtEnd_ThenDecode() {
        String encodedString = "This is a test \\u0046\\u0072\\u006F\\u006D the end.";
        String expectedDecodedString = "This is a test From the end.";
        assertEquals(expectedDecodedString, UnicodeConverterUtil.decodeWithApacheCommons(encodedString));
        assertEquals(expectedDecodedString, UnicodeConverterUtil.decodeWithPlainJava(encodedString));
    }

    @Test
    public void whenInputHaveMultipleUnicodeSequences_ThenDecode() {
        String encodedString = "Unicode: \\u0048\\u0065\\u006C\\u006C\\u006F \\u0057\\u006F\\u0072\\u006C\\u0064";
        String expectedDecodedString = "Unicode: Hello World";
        assertEquals(expectedDecodedString, UnicodeConverterUtil.decodeWithApacheCommons(encodedString));
        assertEquals(expectedDecodedString, UnicodeConverterUtil.decodeWithPlainJava(encodedString));
    }
}
