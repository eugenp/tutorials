package com.baeldung.urlencoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SpaceURLEncoderUnitTest {

    @Test
    void givenSpaceInString_whenUsingDefaultEncoding_thenReturnPlusSign() {
        String originalString = "Welcome to the Baeldung Website!";
        String encodedString = URLEncoder.encode(originalString);
        assertEquals("Welcome+to+the+Baeldung+Website%21", encodedString);
    }

    @Test
    void givenSpaceInString_whenUsingUTF8Encoding_thenReturnPlusSign() throws UnsupportedEncodingException {
        String originalString = "Welcome to the Baeldung Website!";
        String encodedString = URLEncoder.encode(originalString, StandardCharsets.UTF_8.toString());
        assertEquals("Welcome+to+the+Baeldung+Website%21", encodedString);
    }

    @Test
    void givenSpaceInString_whenUsingDefaultEncodingAndReplace_thenReturnPct20() throws UnsupportedEncodingException {
        String originalString = "Welcome to the Baeldung Website!";
        String encodedString = URLEncoder.encode(originalString)
            .replace("+", "%20");
        assertEquals("Welcome%20to%20the%20Baeldung%20Website%21", encodedString);
    }

    @Test
    void givenSpaceInString_whenUsingDefaultEncodingAndReplaceAll_thenReturnPct20() throws UnsupportedEncodingException {
        String originalString = "Welcome to the Baeldung Website!";
        String encodedString = URLEncoder.encode(originalString)
            .replaceAll("\\+", "%20");
        assertEquals("Welcome%20to%20the%20Baeldung%20Website%21", encodedString);
    }
}
