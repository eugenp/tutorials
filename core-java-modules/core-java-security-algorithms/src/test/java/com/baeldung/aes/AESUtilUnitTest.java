package com.baeldung.aes;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class AESUtilUnitTest implements WithAssertions {

    @Test
    void givenString_whenEncrypt_thenSuccess()
        throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
        BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        String input = "baeldung";
        SecretKey key = AESUtil.generateKey(128);
        GCMParameterSpec gcmParameterSpec = AESUtil.generateIv();
        String algorithm = "AES/GCM/NoPadding";

        // when
        String cipherText = AESUtil.encrypt(algorithm, input, key, gcmParameterSpec);
        String plainText = AESUtil.decrypt(algorithm, cipherText, key, gcmParameterSpec);

        // then
        Assertions.assertEquals(input, plainText);
    }

    @Test
    void givenFile_whenEncrypt_thenSuccess()
        throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, InvalidKeyException,
        BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        SecretKey key = AESUtil.generateKey(128);
        String algorithm = "AES/GCM/NoPadding";
        GCMParameterSpec gcmParameterSpec = AESUtil.generateIv();
        File inputFile = Paths.get("src/test/resources/baeldung.txt")
            .toFile();
        File encryptedFile = new File("baeldung.encrypted");
        File decryptedFile = new File("document.decrypted");

        // when
        AESUtil.encryptFile(algorithm, key, gcmParameterSpec, inputFile, encryptedFile);
        AESUtil.decryptFile(algorithm, key, gcmParameterSpec, encryptedFile, decryptedFile);

        // then
        assertThat(inputFile).hasSameTextualContentAs(decryptedFile);
        encryptedFile.deleteOnExit();
        decryptedFile.deleteOnExit();
    }

    @Test
    void givenObject_whenEncrypt_thenSuccess()
        throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
        InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, BadPaddingException,
        ClassNotFoundException {
        // given
        Student student = new Student("Baeldung", 20);
        SecretKey key = AESUtil.generateKey(128);
        GCMParameterSpec gcmParameterSpec = AESUtil.generateIv();
        String algorithm = "AES/GCM/NoPadding";

        // when
        SealedObject sealedObject = AESUtil.encryptObject(algorithm, student, key, gcmParameterSpec);
        Student object = (Student) AESUtil.decryptObject(algorithm, sealedObject, key, gcmParameterSpec);

        // then
        assertThat(student).isEqualTo(object);
    }

    @Test
    void givenPassword_whenEncrypt_thenSuccess()
        throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException,
        InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        String plainText = "www.baeldung.com";
        String password = "baeldung";
        String salt = "12345678";
        GCMParameterSpec gcmParameterSpec = AESUtil.generateIv();
        SecretKey key = AESUtil.getKeyFromPassword(password, salt);

        // when
        String cipherText = AESUtil.encryptPasswordBased(plainText, key, gcmParameterSpec);
        String decryptedCipherText = AESUtil.decryptPasswordBased(cipherText, key, gcmParameterSpec);

        // then
        Assertions.assertEquals(plainText, decryptedCipherText);
    }
}
