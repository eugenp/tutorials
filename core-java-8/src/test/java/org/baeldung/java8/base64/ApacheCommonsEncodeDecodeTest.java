package org.baeldung.java8.base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class ApacheCommonsEncodeDecodeTest {

    @Test
    public void whenStringIsEncoded() throws UnsupportedEncodingException {
        String originalInput = "test input";
        Base64 base64 = new Base64();
        String encodedString = new String(base64.encode(originalInput.getBytes()));

        assertNotNull(encodedString);
        assertNotEquals(originalInput, encodedString);
    }

    @Test
    public void whenStringIsEncoded_thenStringCanBeDecoded() throws UnsupportedEncodingException {
        String originalInput = "test input";
        Base64 base64 = new Base64();
        String encodedString = new String(base64.encode(originalInput.getBytes()));

        String decodedString = new String(base64.decode(encodedString.getBytes()));

        assertNotNull(decodedString);
        assertEquals(originalInput, decodedString);
    }

    @Test
    public void whenStringIsEncodedUsingStaticMethod() throws UnsupportedEncodingException {
        String originalInput = "test input";
        String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));

        assertNotNull(encodedString);
        assertNotEquals(originalInput, encodedString);
    }

    @Test
    public void whenStringIsEncodedUsingStaticMethod_thenStringCanBeDecodedUsingStaticMethod() throws UnsupportedEncodingException {
        String originalInput = "test input";
        String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));

        String decodedString = new String(Base64.decodeBase64(encodedString.getBytes()));

        assertNotNull(decodedString);
        assertEquals(originalInput, decodedString);
    }

}
