package com.baeldung.integersatbitlevel;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class IntegersBitLevelUnitTest {
    @Test
    public void givenKeyAndPlaintext_whenEncryptingPlaintext_thenRetrieveCiphertext() {
        int key = 0b10101010;
        int plaintext = 0b11001100;
        int ciphertext = plaintext ^ key;

        assertEquals(ciphertext, 0b01100110);
    }

    @Test
    public void givenDataANDMask_whenApplyingMask_thenRetrieveIsolatedBits() {
        int data = 0b11010110;
        int mask = 0b00001111;
        int isolatedBits = data & mask;

        assertEquals(isolatedBits, 6);
    }
}
