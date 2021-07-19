package com.baeldung.base64encodinganddecoding;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ApacheCommonsEncodeDecodeUnitTest {

    // tests

    @Test
    public void whenStringIsEncoded() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final Base64 base64 = new Base64();
        final String encodedString = new String(base64.encode(originalInput.getBytes()));

        assertNotNull(encodedString);
        assertNotEquals(originalInput, encodedString);
    }

    @Test
    public void whenStringIsEncoded_thenStringCanBeDecoded() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final Base64 base64 = new Base64();
        final String encodedString = new String(base64.encode(originalInput.getBytes()));

        final String decodedString = new String(base64.decode(encodedString.getBytes()));

        assertNotNull(decodedString);
        assertEquals(originalInput, decodedString);
    }

    @Test
    public void whenStringIsEncodedUsingStaticMethod() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));

        assertNotNull(encodedString);
        assertNotEquals(originalInput, encodedString);
    }

    @Test
    public void whenStringIsEncodedUsingStaticMethod_thenStringCanBeDecodedUsingStaticMethod() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));

        final String decodedString = new String(Base64.decodeBase64(encodedString.getBytes()));

        assertNotNull(decodedString);
        assertEquals(originalInput, decodedString);
    }

}
