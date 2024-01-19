package com.baeldung.unicodechar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnicodeCharFromCodePointHexStringUnitTest {
    private static final String U_CHECK = "✅"; // U+2705
    private static final String U_STRONG = "强"; // U+5F3A


    @Test
    void whenEscapeUAndNumberInString_thenGetExpectedUnicodeStr() {
        String check = "\u2705";
        assertEquals(U_CHECK, check);

        String strong = "\u5F3A";
        assertEquals(U_STRONG, strong);

        // "A" U+0041
        assertEquals("A", "\u0041");
    }


    @Test
    void whenConcatUAndNumberAsString_thenDoNotGetExpectedUnicodeStr() {
        String check = "\\u" + "2705";
        assertEquals("\\u2705", check);

        String strong = "\\u" + "5F3A";
        assertEquals("\\u5F3A", strong);
    }


    @Test
    void whenCastHexCodePointToCharAndConvertCharToString_thenGetExpectedUnicodeStr() {

        int codePoint = Integer.parseInt("2705", 16); //Decimal int: 9989
        char[] checkChar = Character.toChars(codePoint);
        String check = String.valueOf(checkChar);
        assertEquals(U_CHECK, check);

        // For Java 11 and later versions
        // assertEquals(U_CHECK, Character.toString(codePoint));

        codePoint = Integer.parseInt("5F3A", 16); //Decimal int: 24378
        char[] strongChar = Character.toChars(codePoint);
        String strong = String.valueOf(strongChar);
        assertEquals(U_STRONG, strong);

        // For Java 11 and later versions
        // assertEquals(U_STRONG, Character.toString(codePoint));
    }

    String stringFromCodePointHex(String codePointHex) {
        int codePoint = Integer.parseInt(codePointHex, 16);

        // For Java 11 and later versions: return Character.toString(codePoint)

        char[] chars = Character.toChars(codePoint);
        return String.valueOf(chars);
    }

    @Test
    void whenUsingstringFromCodePointHex_thenGetExpectedUnicodeStr() {
        assertEquals("A", stringFromCodePointHex("0041"));
        assertEquals(U_CHECK, stringFromCodePointHex("2705"));
        assertEquals(U_STRONG, stringFromCodePointHex("5F3A"));
    }
}