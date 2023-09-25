package com.baeldung.crypto.sha1;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import com.google.common.hash.Hashing;

public class HexRepresentationSha1DigestUnitTest {

    String input = "Hello, World";
    String expectedHexValue = "907d14fb3af2b0d4f18c2d46abe8aedce17367bd";

    @Test
    public void givenMessageDigest_whenUpdatingWithData_thenDigestShouldMatchExpectedValue() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        byte[] digest = md.digest();

        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        assertEquals(expectedHexValue, hexString.toString());
    }

    @Test
    public void givenDigestUtils_whenCalculatingSHA1Hex_thenDigestShouldMatchExpectedValue() {
        assertEquals(expectedHexValue, DigestUtils.sha1Hex(input));
    }

    @Test
    public void givenHashingLibrary_whenCalculatingSHA1Hash_thenDigestShouldMatchExpectedValue() {
        assertEquals(expectedHexValue, Hashing.sha1().hashString(input, StandardCharsets.UTF_8).toString());
    }
}
