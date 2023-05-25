package com.baeldung.crypto.exception;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class InvalidAlgorithmParameterExamples {

    public static byte[] encryptUsingIv(SecretKey key, byte[] ivBytes, String plainText)
            throws InvalidKeyException, GeneralSecurityException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);

        byte[] bytes = plainText.getBytes();

        return cipher.doFinal(bytes);
    }
}
