package com.baeldung.crypto.exception;

import java.security.GeneralSecurityException;

import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.crypto.utils.CryptoUtils;

public class BadPaddingExamplesUnitTest {

    private SecretKey key;
    private IvParameterSpec ivParameterSpec;
    private String plainText;
    private byte[] plainTextBytes;

    @Before
    public void before() throws GeneralSecurityException {
        key = CryptoUtils.getFixedKey();

        byte[] ivBytes = new byte[] { 'B', 'a', 'e', 'l', 'd', 'u', 'n', 'g', 'I', 's', 'G', 'r', 'e', 'a', 't', '!' };
        ivParameterSpec = new IvParameterSpec(ivBytes);

        plainText = "https://www.baeldung.com/";
        plainTextBytes = plainText.getBytes();
    }

    @Test
    public void givenTwoDifferentAlgorithmPaddings_whenDecrypting_thenBadPaddingExceptionIsThrown()
            throws GeneralSecurityException {
        Assert.assertThrows(BadPaddingException.class,
                () -> BadPaddingExamples.encryptAndDecryptUsingDifferentPaddings(key, plainTextBytes));
    }

    @Test
    public void givenTwoDifferentKeys_whenDecrypting_thenBadPaddingExceptionIsThrown() throws GeneralSecurityException {
        Assert.assertThrows(BadPaddingException.class,
                () -> BadPaddingExamples.encryptAndDecryptUsingDifferentKeys(plainTextBytes));
    }

    @Test
    public void givenTwoDifferentAlgorithms_whenDecrypting_thenBadPaddingExceptionIsThrown()
            throws GeneralSecurityException {
        Assert.assertThrows(BadPaddingException.class, () -> BadPaddingExamples
                .encryptAndDecryptUsingDifferentAlgorithms(key, ivParameterSpec, plainTextBytes));
    }

    @Test
    public void givenSameVariablesUsedForEncryptingAndDecrypting_whenDecrypting_thenNoExceptionIsThrown()
            throws GeneralSecurityException {
        byte[] decryptedBytes = BadPaddingExamples.encryptAndDecryptUsingSamePaddingKeyAndAlgorithm(key,
                plainTextBytes);

        Assert.assertEquals(plainText, new String(decryptedBytes));
    }
}
