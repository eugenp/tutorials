package com.baeldung.hashing;

import org.junit.Test;

import static org.junit.Assert.*;

public class SHA256HashingTest {

    private static String originalValue = "abc123";
    private static String hashedValue = "6ca13d52ca70c883e0f0bb101e425a89e8624de51db2d2392593af6a84118090";

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = SHA256Hashing.HashWithJavaMessageDigest(originalValue);
        assertEquals(currentHashedValue, hashedValue);
    }

    @Test
    public void testHashWithGuava() throws Exception {
        final String currentHashedValue = SHA256Hashing.HashWithApacheCommons(originalValue);
        assertEquals(currentHashedValue, hashedValue);
    }

    @Test
    public void testHashWithApacheCommans() throws Exception {
        final String currentHashedValue = SHA256Hashing.HashWithGuava(originalValue);
        assertEquals(currentHashedValue, hashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() throws Exception {
        final String currentHashedValue = SHA256Hashing.HashWithBouncyCastle(originalValue);
        assertEquals(currentHashedValue, hashedValue);
    }
}