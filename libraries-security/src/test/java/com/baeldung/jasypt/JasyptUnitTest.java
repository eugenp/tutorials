package com.baeldung.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class JasyptUnitTest {

    @Test
    public void givenTextPrivateData_whenDecrypt_thenCompareToEncrypted() {
        // given
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        String privateData = "secret-data";
        textEncryptor.setPasswordCharArray("some-random-data".toCharArray());

        // when
        String myEncryptedText = textEncryptor.encrypt(privateData);
        assertNotSame(privateData, myEncryptedText); // myEncryptedText can be save in db

        // then
        String plainText = textEncryptor.decrypt(myEncryptedText);
        assertEquals(plainText, privateData);
    }

    @Test
    public void givenTextPassword_whenOneWayEncryption_thenCompareEncryptedPasswordsShouldBeSame() {
        String password = "secret-pass";
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(password);

        // when
        boolean result = passwordEncryptor.checkPassword("secret-pass", encryptedPassword);

        // then
        assertTrue(result);
    }

    @Test
    public void givenTextPassword_whenOneWayEncryption_thenCompareEncryptedPasswordsShouldNotBeSame() {
        String password = "secret-pass";
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(password);

        // when
        boolean result = passwordEncryptor.checkPassword("secret-pass-not-same", encryptedPassword);

        // then
        assertFalse(result);
    }

    @Test
    @Ignore("should have installed local_policy.jar")
    public void givenTextPrivateData_whenDecrypt_thenCompareToEncryptedWithCustomAlgorithm() {
        // given
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String privateData = "secret-data";
        encryptor.setPassword("some-random-data");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        // when
        String encryptedText = encryptor.encrypt("secret-pass");
        assertNotSame(privateData, encryptedText);

        // then
        String plainText = encryptor.decrypt(encryptedText);
        assertEquals(plainText, privateData);
    }

    @Test
    @Ignore("should have installed local_policy.jar")
    public void givenTextPrivateData_whenDecryptOnHighPerformance_thenDecrypt() {
        // given
        String privateData = "secret-data";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword("some-random-data");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        // when
        String encryptedText = encryptor.encrypt(privateData);
        assertNotSame(privateData, encryptedText);

        // then
        String plainText = encryptor.decrypt(encryptedText);
        assertEquals(plainText, privateData);
    }
}
