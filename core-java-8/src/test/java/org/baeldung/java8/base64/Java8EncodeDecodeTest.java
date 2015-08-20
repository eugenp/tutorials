package org.baeldung.java8.base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

import org.junit.Test;

public class Java8EncodeDecodeTest {

    // tests

    @Test
    public void whenStringIsEncoded() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        assertNotNull(encodedString);
        assertNotEquals(originalInput, encodedString);
    }

    @Test
    public void whenStringIsEncoded_thenStringCanBeDecoded() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        final byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        final String decodedString = new String(decodedBytes);

        assertNotNull(decodedString);
        assertEquals(originalInput, decodedString);

    }

    @Test
    public void whenStringIsEncodedWithoutPadding() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final String encodedString = Base64.getEncoder().withoutPadding().encodeToString(originalInput.getBytes());
        assertNotNull(encodedString);
        assertNotEquals(originalInput, encodedString);
    }

    @Test
    public void whenStringIsEncodedWithoutPadding_thenStringCanBeDecoded() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        final String encodedString = Base64.getEncoder().withoutPadding().encodeToString(originalInput.getBytes());

        final byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        final String decodedString = new String(decodedBytes);

        assertNotNull(decodedString);
        assertEquals(originalInput, decodedString);

    }

    @Test
    public void whenURLIsEncoded() throws UnsupportedEncodingException {
        final String originalURL = "https://www.google.co.nz/?gfe_rd=cr&ei=dzbFVf&gws_rd=ssl#q=java";
        final String encodedURL = Base64.getUrlEncoder().encodeToString(originalURL.getBytes());
        assertNotNull(encodedURL);
        assertNotEquals(originalURL, encodedURL);
    }

    @Test
    public void whenURLIsEncoded_thenURLCanBeDecoded() throws UnsupportedEncodingException {
        final String originalURL = "https://www.google.co.nz/?gfe_rd=cr&ei=dzbFVf&gws_rd=ssl#q=java";
        final String encodedURL = Base64.getUrlEncoder().encodeToString(originalURL.getBytes());
        final byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedURL.getBytes());
        final String decodedURL = new String(decodedBytes);
        assertNotNull(decodedURL);
        assertEquals(originalURL, decodedURL);
    }

    @Test
    public void whenMIMEIsEncoded() throws UnsupportedEncodingException {
        final StringBuilder buffer = getMimeBuffer();

        final byte[] forEncode = buffer.toString().getBytes();
        final String encodedMime = Base64.getMimeEncoder().encodeToString(forEncode);

        assertNotNull(encodedMime);
    }

    @Test
    public void whenMIMEIsEncoded_thenMIMECanBeDecoded() throws UnsupportedEncodingException {
        final StringBuilder buffer = getMimeBuffer();

        final byte[] forEncode = buffer.toString().getBytes();
        final String encodedMime = Base64.getMimeEncoder().encodeToString(forEncode);

        final byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedMime);
        final String decodedMime = new String(decodedBytes);
        assertNotNull(decodedMime);
    }

    //

    private static StringBuilder getMimeBuffer() {
        final StringBuilder buffer = new StringBuilder();
        for (int count = 0; count < 10; ++count) {
            buffer.append(UUID.randomUUID().toString());
        }
        return buffer;
    }

}
