package com.baeldung.crypto.exception;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.baeldung.crypto.utils.CryptoUtils;

public class IllegalBlockSizeExamples {

    public static byte[] encryptWithoutPadding(SecretKey key, byte[] plainTextBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(plainTextBytes);
    }

    public static byte[] decryptTextThatIsNotEncrypted(SecretKey key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // note that this text is not encrypted at any point in this method.
        String sampleText = "https://www.baeldung.com/";
        byte[] unencryptedCipherTextBytes = sampleText.getBytes();

        return CryptoUtils.decryptWithPadding(key, unencryptedCipherTextBytes);
    }
}
