package com.baeldung.checkifstringisbased64;

import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckIfStringIsBased64UnitTest {

    @Test
    public void givenString_whenOperatingBase64_thenCheckIfItIsBase64Encoded() {

        try {
            Base64.getDecoder().decode("SGVsbG8gd29ybGQ=");
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            assertFalse(false);
        }

        try {
            Base64.getDecoder().decode("Hello world!");
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            assertFalse(false);
        }
    }

    @Test
    public void givenString_whenOperatingRegex_thenCheckIfItIsBase64Encoded() {
        Pattern BASE64_PATTERN = Pattern.compile(
                "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$"
        );

        assertTrue(BASE64_PATTERN.matcher("SGVsbG8gd29ybGQ=").matches());
        assertFalse(BASE64_PATTERN.matcher("Hello world!").matches());
    }
}