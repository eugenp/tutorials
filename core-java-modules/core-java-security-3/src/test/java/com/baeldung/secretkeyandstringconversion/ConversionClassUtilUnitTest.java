package com.baeldung.secretkeyandstringconversion;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversionClassUtilUnitTest {

    @Test
    void givenPasswordAndSalt_whenCreateSecreKeyCheckConversion_thenSuccess()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // given
        String password = "Baeldung@2021";
        String salt = "@$#baelDunG@#^$*";

        // when
        SecretKey encodedKey = ConversionClassUtil.getKeyFromPassword(password, salt);
        String encodedString = ConversionClassUtil.convertSecretKeyToString(encodedKey);
        SecretKey decodeKey = ConversionClassUtil.convertStringToSecretKeyto(encodedString);

        // then
        Assertions.assertEquals(encodedKey, decodeKey);
    }

    @Test
    void givenSize_whenCreateSecreKeyCheckConversion_thenSuccess()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // given
        int size = 256;

        // when
        SecretKey encodedKey = ConversionClassUtil.generateKey(size);
        String encodedString = ConversionClassUtil.convertSecretKeyToString(encodedKey);
        SecretKey decodeKey = ConversionClassUtil.convertStringToSecretKeyto(encodedString);

        // then
        Assertions.assertEquals(encodedKey, decodeKey);
    }

}
