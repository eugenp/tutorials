package com.baeldung.digitalsignature;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.Cipher;

import org.junit.Test;

public class DigitalSignatureTest {
    @Test
    public void useOaep() throws Exception {

        Utils.getProvider();

        PublicKey publicKey = Utils.getPublicKey();
        PrivateKey privateKey = Utils.getPrivateKey();

        byte[] messageBytes = Files.readAllBytes(Paths.get("src/test/resources/digitalsignature/message.txt"));
        MessageDigest md = MessageDigest.getInstance(Utils.MD_ALGORITHM);
        byte[] messageHash = md.digest(messageBytes);

        // Encryption algorithms should be used with secure mode and padding scheme
        Cipher cipher = Cipher.getInstance(Utils.CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedMessageHash = cipher.doFinal(messageHash);

        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);

        String mhStr = Arrays.toString(messageHash);
        String dmhStr = Arrays.toString(decryptedMessageHash);
        assertEquals(mhStr, dmhStr);
    }
}
