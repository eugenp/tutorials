package com.baeldung.crypto.exception;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.baeldung.crypto.utils.CryptoUtils;

public class InvalidKeyExamples {

    public static byte[] decryptUsingCBCWithNoIV(SecretKey key, byte[] cipherTextBytes)
            throws InvalidKeyException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(cipherTextBytes);
    }

    public static byte[] decryptUsingCBCWithIV(SecretKey key, byte[] cipherTextBytes)
            throws InvalidKeyException, GeneralSecurityException {
        byte[] ivBytes = new byte[] { 'B', 'a', 'e', 'l', 'd', 'u', 'n', 'g', 'I', 's', 'G', 'r', 'e', 'a', 't', '!' };
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

        return cipher.doFinal(cipherTextBytes);
    }

    public static byte[] encryptWithKeyTooShort() throws InvalidKeyException, GeneralSecurityException {
        SecretKey encryptionKey = CryptoUtils.getKeyForText("ThisIsTooShort");

        String plainText = "https://www.baeldung.com/";
        byte[] bytes = plainText.getBytes();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
        return cipher.doFinal(bytes);
    }

    public static byte[] encryptWithKeyTooLong() throws InvalidKeyException, GeneralSecurityException {
        SecretKey encryptionKey = CryptoUtils.getKeyForText("ThisTextIsTooLong");

        String plainText = "https://www.baeldung.com/";
        byte[] bytes = plainText.getBytes();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
        return cipher.doFinal(bytes);
    }
}
