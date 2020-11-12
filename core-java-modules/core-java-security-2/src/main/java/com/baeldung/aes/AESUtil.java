package com.baeldung.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder()
                .encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
            InvalidKeyException | InvalidAlgorithmParameterException |
            IllegalBlockSizeException | BadPaddingException exp) {
            LOGGER.error(exp.getMessage());
        }
        return null;
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
            return new String(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
            InvalidKeyException | InvalidAlgorithmParameterException |
            IllegalBlockSizeException | BadPaddingException exp) {
            LOGGER.error(exp.getMessage());
        }
        return null;
    }

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
            .getEncoded(), "AES");
        return secret;
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
        File inputFile, File outputFile) throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException |
            NoSuchPaddingException | BadPaddingException |
            IllegalBlockSizeException | InvalidKeyException exp) {
            LOGGER.error(exp.getMessage());
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    public static void decryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
        File encryptedFile, File decryptedFile) throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            inputStream = new FileInputStream(encryptedFile);
            outputStream = new FileOutputStream(decryptedFile);
            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                outputStream.write(output);
            }
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException |
            NoSuchPaddingException | BadPaddingException |
            IllegalBlockSizeException | InvalidKeyException exp) {
            LOGGER.error(exp.getMessage());
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    public static SealedObject encryptObject(String algorithm, Serializable object, SecretKey key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            SealedObject sealedObject = new SealedObject(object, cipher);
            return sealedObject;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
            InvalidKeyException | InvalidAlgorithmParameterException |
            IOException | IllegalBlockSizeException exp) {
            LOGGER.error(exp.getMessage());
        }
        return null;
    }

    public static Serializable decryptObject(String algorithm, SealedObject sealedObject,
        SecretKey key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            Serializable unsealObject = (Serializable) sealedObject.getObject(cipher);
            return unsealObject;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
            InvalidKeyException | InvalidAlgorithmParameterException |
            IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException exp) {
            LOGGER.error(exp.getMessage());
        }
        return null;
    }

    public static String encryptPasswordBased(String plainText, SecretKey key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return Base64.getEncoder()
                .encodeToString(cipher.doFinal(plainText.getBytes()));
        } catch (NoSuchAlgorithmException | BadPaddingException |
            InvalidKeyException | InvalidAlgorithmParameterException |
            NoSuchPaddingException | IllegalBlockSizeException exp) {
            LOGGER.error(exp.getMessage());
        }
        return null;
    }

    public static String decryptPasswordBased(String cipherText, SecretKey key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            return new String(cipher.doFinal(Base64.getDecoder()
                .decode(cipherText)));
        } catch (NoSuchAlgorithmException | BadPaddingException |
            InvalidKeyException | InvalidAlgorithmParameterException |
            NoSuchPaddingException | IllegalBlockSizeException exp) {
            LOGGER.error(exp.getMessage());
        }
        return null;
    }

}
