package com.baeldung.checkifstringcontainsinvalidcharacters;

import org.junit.jupiter.api.Test;

import java.nio.charset.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckIfStringContainsInvalidEncodedCharactersUnitTest {

    public String input = "HÆllo, World!";

    @Test
    public void givenInputString_whenUsingRegexPattern_thenFindIfInvalidCharacters() {
        String regexPattern = "[^\\x00-\\x7F]+";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    @Test
    public void givenInputString_whenUsingStringEncoding_thenFindIfInvalidCharacters() {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        boolean found = false;
        for (byte b : bytes) {
            if ((b & 0xFF) > 127) {
                found = true;
            }
        }
        if (found) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }
}
