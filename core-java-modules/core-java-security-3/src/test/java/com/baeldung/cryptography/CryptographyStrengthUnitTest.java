package com.baeldung.cryptography;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptographyStrengthUnitTest {
    private static final int UNLIMITED_KEY_SIZE = 2147483647;

    @Test
    public void whenDefaultCheck_thenUnlimitedReturned() throws NoSuchAlgorithmException {
        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        assertThat(maxKeySize).isEqualTo(UNLIMITED_KEY_SIZE);
    }
}
