package com.baeldung.base64encodinganddecoding;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

import static org.junit.Assert.*;

public class Java8EncodeDecodeUnitTest {

    // tests

    @Test
    public void whenStringIsEncoded_thenOk() throws UnsupportedEncodingException {
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
    public void whenStringIsEncodedWithoutPadding_thenOk() throws UnsupportedEncodingException {
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
    public void whenUrlIsEncoded_thenOk() throws UnsupportedEncodingException {
        final String originalUrl = "https://www.google.co.nz/?gfe_rd=cr&ei=dzbFVf&gws_rd=ssl#q=java";
        final String encodedUrl = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes());
        assertNotNull(encodedUrl);
        assertNotEquals(originalUrl, encodedUrl);
    }

    @Test
    public void whenUrlIsEncoded_thenURLCanBeDecoded() throws UnsupportedEncodingException {
        final String originalUrl = "https://www.google.co.nz/?gfe_rd=cr&ei=dzbFVf&gws_rd=ssl#q=java";
        final String encodedUrl = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes());

        final byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedUrl.getBytes());
        final String decodedUrl = new String(decodedBytes);

        assertNotNull(decodedUrl);
        assertEquals(originalUrl, decodedUrl);
    }

    @Test
    public void whenMimeIsEncoded_thenOk() throws UnsupportedEncodingException {
        final StringBuilder buffer = getMimeBuffer();

        final byte[] forEncode = buffer.toString().getBytes();
        final String encodedMime = Base64.getMimeEncoder().encodeToString(forEncode);

        assertNotNull(encodedMime);
    }

    @Test
    public void whenMimeIsEncoded_thenItCanBeDecoded() throws UnsupportedEncodingException {
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
