package com.baeldung.secretkey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

import static javax.crypto.Cipher.ENCRYPT_MODE;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String CIPHER = "AES";

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        String plainText = "How you doing Mike?";

        encrypt(plainText, getRandomKey(CIPHER, 128));
        encrypt(plainText, getRandomKey(CIPHER, 192));
        encrypt(plainText, getRandomKey(CIPHER, 256));

        encrypt(plainText, getSecureRandomKey(CIPHER, 128));
        encrypt(plainText, getSecureRandomKey(CIPHER, 192));
        encrypt(plainText, getSecureRandomKey(CIPHER, 256));

        encrypt(plainText, getKeyGeneratorKey(CIPHER, 128));
        encrypt(plainText, getKeyGeneratorKey(CIPHER, 192));
        encrypt(plainText, getKeyGeneratorKey(CIPHER, 256));

        encrypt(plainText, getKeyFromPassword(CIPHER, 128, new char[] {'R', 'a', 'n', 'd', 'o', 'm'}));
        encrypt(plainText, getKeyFromPassword(CIPHER, 192, new char[] {'R', 'a', 'n', 'd', 'o', 'm'}));
        encrypt(plainText, getKeyFromPassword(CIPHER, 256, new char[] {'R', 'a', 'n', 'd', 'o', 'm'}));
    }

    private static Key getRandomKey(String cipher, int keySize) {
        byte[] randomKeyBytes = new byte[keySize / 8];
        Random random = new Random();
        random.nextBytes(randomKeyBytes);

        return new SecretKeySpec(randomKeyBytes, cipher);
    }

    private static Key getSecureRandomKey(String cipher, int keySize) {
        byte[] secureRandomKeyBytes = new byte[keySize / 8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secureRandomKeyBytes);

        return new SecretKeySpec(secureRandomKeyBytes, cipher);
    }

    private static Key getKeyGeneratorKey(String cipher, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(cipher);
        keyGenerator.init(keySize);
        Key key = keyGenerator.generateKey();

        return new SecretKeySpec(key.getEncoded(), cipher);
    }

    private static Key getKeyFromPassword(String cipher, int keySize, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Random random = new Random();
        byte[] salt = new byte[100];
        random.nextBytes(salt);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, 10, keySize);
        SecretKey pbeKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pbeKeySpec);

        return new SecretKeySpec(pbeKey.getEncoded(), cipher);
    }

    private static void encrypt(String plainText, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(ENCRYPT_MODE, key);
        byte[] cipherTextBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        logger.info(Base64.getEncoder().encodeToString(cipherTextBytes));
    }
}
