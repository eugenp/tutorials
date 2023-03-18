package com.baeldung.crypto.exception;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.baeldung.crypto.utils.CryptoUtils;

public class BadPaddingExamples {

    public static byte[] encryptAndDecryptUsingDifferentKeys(byte[] plainTextBytes)
            throws InvalidKeyException, GeneralSecurityException {
        SecretKey encryptionKey = CryptoUtils.getKeyForText("BaeldungIsASuperCoolSite");
        SecretKey differentKey = CryptoUtils.getKeyForText("ThisGivesUsAnAlternative");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
        byte[] cipherTextBytes = cipher.doFinal(plainTextBytes);

        cipher.init(Cipher.DECRYPT_MODE, differentKey);

        return cipher.doFinal(cipherTextBytes);
    }

    public static byte[] encryptAndDecryptUsingDifferentAlgorithms(SecretKey key, IvParameterSpec ivParameterSpec,
            byte[] plainTextBytes) throws InvalidKeyException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] cipherTextBytes = cipher.doFinal(plainTextBytes);

        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(cipherTextBytes);
    }

    public static byte[] encryptAndDecryptUsingDifferentPaddings(SecretKey key, byte[] plainTextBytes)
            throws InvalidKeyException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/ISO10126Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherTextBytes = cipher.doFinal(plainTextBytes);

        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(cipherTextBytes);
    }

    public static byte[] encryptAndDecryptUsingSamePaddingKeyAndAlgorithm(SecretKey key, byte[] plainTextBytes)
            throws InvalidKeyException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherTextBytes = cipher.doFinal(plainTextBytes);

        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(cipherTextBytes);
    }
}
