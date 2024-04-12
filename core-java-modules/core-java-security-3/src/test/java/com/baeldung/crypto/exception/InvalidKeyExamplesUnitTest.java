package com.baeldung.crypto.exception;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.crypto.utils.CryptoUtils;

public class InvalidKeyExamplesUnitTest {

    private SecretKey key;
    private String plainText;
    private byte[] cipherTextBytes;

    @Before
    public void before() throws GeneralSecurityException {
        key = CryptoUtils.getFixedKey();

        byte[] ivBytes = new byte[] { 'B', 'a', 'e', 'l', 'd', 'u', 'n', 'g', 'I', 's', 'G', 'r', 'e', 'a', 't', '!' };
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);

        plainText = "https://www.baeldung.com/";
        byte[] plainTextBytes = plainText.getBytes();

        cipherTextBytes = cipher.doFinal(plainTextBytes);
    }

    @Test
    public void givenTextEncryptedWithCBC_whenDecryptingWithNoIv_thenInvalidKeyExceptionIsThrown() {
        Assert.assertThrows(InvalidKeyException.class,
                () -> InvalidKeyExamples.decryptUsingCBCWithNoIV(key, cipherTextBytes));
    }

    @Test
    public void givenTextEncryptedWithCBC_whenDecryptingWithIv_thenTextIsDecrypted()
            throws InvalidKeyException, GeneralSecurityException {
        byte[] decryptedBytes = InvalidKeyExamples.decryptUsingCBCWithIV(key, cipherTextBytes);

        Assert.assertEquals(plainText, new String(decryptedBytes));
    }

    @Test
    public void whenKeyIsTooShort_thenInvalidKeyExceptionIsThrown() {
        Assert.assertThrows(InvalidKeyException.class, () -> InvalidKeyExamples.encryptWithKeyTooShort());
    }

    @Test
    public void whenKeyIsTooLong_thenInvalidKeyExceptionIsThrown() {
        Assert.assertThrows(InvalidKeyException.class, () -> InvalidKeyExamples.encryptWithKeyTooLong());
    }

}
