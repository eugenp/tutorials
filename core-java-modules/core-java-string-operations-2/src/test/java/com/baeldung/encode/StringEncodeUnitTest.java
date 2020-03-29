package com.baeldung.encode;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class StringEncodeUnitTest {

    private String asciiEncodedString = new String("Entwickeln Sie mit Vergnügen".getBytes(), StandardCharsets.US_ASCII);

    @Test
    public void givenUsAsciiString_whenComparing_thenCompareNotEquals() {
        assertNotEquals(asciiEncodedString, "Entwickeln Sie mit Vergnügen");
    }

    /*
     * ApacheCommonsCodecEncode
     */
    @Test
    public void givenSomeUnencodedString_whenApacheCommonsCodecEncode_thenCompareEquals() {
        assertEquals(asciiEncodedString, new ApacheCommonsCodecEncode().encodeString(asciiEncodedString));
    }

    /*
     * CoreJavaEncode
     */
    @Test
    public void givenSomeUnencodedString_whenCoreJavaEncode_thenCompareEquals() {
        assertEquals(asciiEncodedString, new CoreJavaEncode().encodeString(asciiEncodedString));
    }

    /*
     * Java7StandardCharsetsEncode
     */
    @Test
    public void givenSomeUnencodedString_whenJava7StandardCharsetsEncode_thenCompareEquals() {
        assertEquals(asciiEncodedString, new Java7StandardCharsetsEncode().encodeString(asciiEncodedString));
    }
}
