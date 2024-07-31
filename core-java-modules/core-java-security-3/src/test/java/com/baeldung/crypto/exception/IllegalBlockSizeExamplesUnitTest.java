package com.baeldung.crypto.exception;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.crypto.utils.CryptoUtils;

public class IllegalBlockSizeExamplesUnitTest {

    private SecretKey key;
    private byte[] plainTextBytes;
    private String plainText;

    @Before
    public void before() throws GeneralSecurityException {
        key = CryptoUtils.getFixedKey();

        plainText = "https://www.baeldung.com/";
        plainTextBytes = plainText.getBytes();
    }

    @Test
    public void whenEncryptingPlainTextWithoutPadding_thenIllegalBlockSizeExceptionIsThrown()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Assert.assertThrows(IllegalBlockSizeException.class,
                () -> IllegalBlockSizeExamples.encryptWithoutPadding(key, plainTextBytes));
    }

    @Test
    public void whenDecryptingCipherTextThatWasNotEncrypted_thenIllegalBlockSizeExceptionIsThrown()
            throws GeneralSecurityException {
        Assert.assertThrows(IllegalBlockSizeException.class,
                () -> IllegalBlockSizeExamples.decryptTextThatIsNotEncrypted(key));
    }

    @Test
    public void whenEncryptingAndDecryptingWithPadding_thenNoExceptionThrown() throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherTextBytes = CryptoUtils.encryptWithPadding(key, plainTextBytes);

        byte[] decryptedBytes = CryptoUtils.decryptWithPadding(key, cipherTextBytes);

        Assert.assertEquals(plainText, new String(decryptedBytes));
    }
}
