package com.baeldung.hashing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SHA3HashingUnitTest {

    private static String originalValue = "abc123";
    private static String hashedValue = "f58fa3df820114f56e1544354379820cff464c9c41cb3ca0ad0b0843c9bb67ee";

    /* works with JDK9+ only */
    //@Test
    public void testHashWithJavaMessageDigestJDK9() throws Exception {
        final String currentHashedValue = SHA3Hashing.hashWithJavaMessageDigestJDK9(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = SHA3Hashing.hashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    /* works with JDK9+ only */
    //@Test
    public void testHashWithApacheCommonsJDK9() {
        final String currentHashedValue = SHA3Hashing.hashWithApacheCommonsJDK9(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() {
        final String currentHashedValue = SHA3Hashing.hashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

}
