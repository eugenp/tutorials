package org.baeldung.jasypt;


import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

public class JasyptTest {

    @Test
    public void givenTextPassword_whenDecrypt_shouldCompareToEncrypted() {
        //given
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        String password = "secret-pass";
        textEncryptor.setPasswordCharArray(password.toCharArray());

        //when
        String myEncryptedText = textEncryptor.encrypt(password);
        assertNotSame(password, myEncryptedText); //myEncryptedText can be save in db

        //then
        String plainText = textEncryptor.decrypt(myEncryptedText);
        assertEquals(plainText, password);
    }


    @Test
    @Ignore("should have installed local_policy.jar")
    public void givenTextPassword_whenDecrypt_shouldCompareToEncryptedWithCustomAlgorithm() {
        //given
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String password = "secret-pass";
        encryptor.setPassword("secret-pass");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        //when
        String encryptedText = encryptor.encrypt("secret-pass");
        assertNotSame(password, encryptedText);

        //then
        String plainText = encryptor.decrypt(encryptedText);
        assertEquals(plainText, password);
    }

    @Test
    @Ignore("should have installed local_policy.jar")
    public void givenTextPassword_whenDecryptOnHighPerformance_shouldDecrypt(){
        //given
        String password = "secret-pass";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        //when
        String encryptedText = encryptor.encrypt(password);
        assertNotSame(password, encryptedText);

        //then
        String plainText = encryptor.decrypt(encryptedText);
        assertEquals(plainText, password);
    }
}
