package com.baeldung.hashing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Keccak256HashingUnitTest {

    private static String originalValue = "abc123";
    private static String hashedValue = "719accc61a9cc126830e5906f9d672d06eab6f8597287095a2c55a8b775e7016";

    @Test public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = Keccak256Hashing.hashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test public void testHashWithBouncyCastle() {
        final String currentHashedValue = Keccak256Hashing.hashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

}
