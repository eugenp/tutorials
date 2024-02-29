package com.baeldung.firstnchars;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.google.common.base.Ascii;

class FirstNCharactersUnitTest {

    @Test
    void givenString_whenUsingSubstringMethod_thenGetFirstChars() {
        String givenInput = "Hello Baeldung Readers";

        assertEquals("He", givenInput.substring(0, 2));
    }

    @Test
    void givenString_whenUsingSubSequenceMethod_thenGetFirstChars() {
        String givenInput = "Welcome";

        assertEquals("Wel", givenInput.subSequence(0, 3));
    }

    @Test
    void givenString_whenUsingStreamApi_thenGetFirstChars() {
        String givenInput = "The world is beautiful";
        String result = givenInput.chars()
          .limit(3)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();

        assertEquals("The", result);
    }

    @Test
    void givenString_whenUsingStringUtilsSubstringMethod_thenGetFirstChars() {
        String givenInput = "Baeldung";

        assertEquals("Baeld", StringUtils.substring(givenInput, 0, 5));
    }

    @Test
    void givenString_whenUsingStringUtilsLeftMethod_thenGetFirstChars() {
        String givenInput = "kindness always wins";

        assertEquals("kind", StringUtils.left(givenInput, 4));
    }

    @Test
    void givenString_whenUsingGuavaTruncateMethod_thenGetFirstChars() {
        String givenInput = "Tamassint";

        assertEquals("Tama", Ascii.truncate(givenInput, 4, ""));
    }

}
