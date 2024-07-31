package com.baeldung.checkifstringisbased64;

import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class CheckIfStringIsBased64UnitTest {

    @Test
    public void givenBase64EncodedString_whenDecoding_thenNoException() {
        try {
            Base64.getDecoder().decode("SGVsbG8gd29ybGQ=");
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void givenNonBase64String_whenDecoding_thenCatchException() {
        try {
            Base64.getDecoder().decode("Hello world!");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void givenString_whenOperatingRegex_thenCheckIfItIsBase64Encoded() {
        Pattern BASE64_PATTERN = Pattern.compile(
                "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$"
        );

        assertTrue(BASE64_PATTERN.matcher("SGVsbG8gd29ybGQ=").matches());
    }
}
