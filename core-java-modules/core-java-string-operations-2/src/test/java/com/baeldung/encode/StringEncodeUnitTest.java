package com.baeldung.encode;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class StringEncodeUnitTest {

    private String unencodedString = new String("Entwickeln Sie mit Vergnügen".getBytes(), StandardCharsets.US_ASCII);

    @Test
    public void givenWhenUsAsciiString_whenCompating_thenCompareNotEquals() {
        assertNotEquals(unencodedString, "Entwickeln Sie mit Vergnügen");
    }

    /*
     * ApacheCommonsCodecEncode
     */
    @Test
    public void givenSomeUnencodedString_whenApacheCommonsCodecEncode_thenCompareEquals() {
        assertEquals(new ApacheCommonsCodecEncode().encodeString(unencodedString), unencodedString);
    }

    /*
     * CoreJavaEncode
     */
    @Test
    public void givenSomeUnencodedString_whenCoreJavaEncode_thenCompareEquals() {
        assertEquals(new CoreJavaEncode().encodeString(unencodedString), unencodedString);
    }

    /*
     * Java7StandardCharsetsEncode
     */
    @Test
    public void givenSomeUnencodedString_whenJava7StandardCharsetsEncode_thenCompareEquals() {
        assertEquals(new Java7StandardCharsetsEncode().encodeString(unencodedString), unencodedString);
    }
}
