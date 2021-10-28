package com.baeldung.crypto;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.crypto.utils.CryptoUtils;

public class CryptoDriverIVUnitTest{
    private CryptoDriver driver = new CryptoDriver();
    private String TEST_DATA = "Encrypt this for testing";

    @Test
    public void givenString_whenAesEcb_thenSuccess() throws GeneralSecurityException {
        SecretKey key = CryptoUtils.generateKey();
        byte[] plaintext = TEST_DATA.getBytes();

        byte[] ciphertext = driver.ecbEncrypt(key, plaintext);
        byte[] decryptedtext = driver.ecbDecrypt(key, ciphertext);

        Assertions.assertEquals(new String(decryptedtext), TEST_DATA);
    }

    @Test
    public void givenString_whenAesCbc_thenSuccess() throws GeneralSecurityException {
        SecretKey key = CryptoUtils.generateKey();
        IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES");
        byte[] plaintext = TEST_DATA.getBytes();

        byte[] ciphertext = driver.cbcEncrypt(key, iv, plaintext);
        byte[] decryptedtext = driver.cbcDecrypt(key, iv, ciphertext);

        Assertions.assertEquals(new String(decryptedtext), TEST_DATA);
    }

    @Test
    public void givenString_whenAesCfb_thenSuccess() throws GeneralSecurityException {
        SecretKey key = CryptoUtils.generateKey();
        IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES/CFB/NoPadding");
        byte[] plaintext = TEST_DATA.getBytes();

        byte[] ciphertext = driver.cfbEncrypt(key, iv, plaintext);
        byte[] decryptedtext = driver.cfbDecrypt(key, iv, ciphertext);

        Assertions.assertEquals(new String(decryptedtext), TEST_DATA);
    }

    @Test
    public void givenString_whenAesOfb_thenSuccess() throws GeneralSecurityException {
        SecretKey key = CryptoUtils.generateKey();
        IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES/OFB32/PKCS5Padding");
        byte[] plaintext = TEST_DATA.getBytes();

        byte[] ciphertext = driver.ofbEncrypt(key, iv, plaintext);
        byte[] decryptedtext = driver.ofbDecrypt(key, iv, ciphertext);

        Assertions.assertEquals(new String(decryptedtext), TEST_DATA);
    }

    @Test
    public void givenString_whenAesCtr_thenSuccess() throws GeneralSecurityException {
        SecretKey key = CryptoUtils.generateKey();
        IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES/CTR/NoPadding");
        byte[] plaintext = TEST_DATA.getBytes();

        byte[][] ciphertext = driver.ctrEncrypt(key, iv, plaintext);
        byte[] decryptedtext = driver.ctrDecrypt(key, ciphertext[0], ciphertext[1]);

        Assertions.assertEquals(new String(decryptedtext), TEST_DATA);
    }

    @Test
    void givenString_whenAesGcm_thenSuccess() throws GeneralSecurityException {
        SecretKey key = CryptoUtils.generateKey();
        byte[] iv = CryptoUtils.getRandomIVWithSize(12);
        byte[] plaintext = (TEST_DATA).getBytes();

        byte[][] ciphertext = driver.gcmEncrypt(key, iv, plaintext);
        byte[] decryptedtext = driver.gcmDecrypt(key, ciphertext[0], ciphertext[1]);

        Assertions.assertEquals(new String(decryptedtext), TEST_DATA);
    }
}
