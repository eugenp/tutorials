package com.baeldung.digitalsignature.level1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.digitalsignature.Utils;

public class DigitalSignatureWithMessageDigestAndCipherVerifying {

    static final Logger log = LoggerFactory.getLogger(DigitalSignatureWithMessageDigestAndCipherVerifying.class);

    public static void main(String[] args) throws Exception {

        PublicKey publicKey = Utils.getPublicKey();

        byte[] messageBytes = Files.readAllBytes(Paths.get("src/test/resources/digitalsignature/message.txt"));

        MessageDigest md = MessageDigest.getInstance(Utils.MD_ALGORITHM);
        byte[] newMessageHash = md.digest(messageBytes);

        byte[] encryptedMessageHash = Files.readAllBytes(Paths.get("target/digital_signature_1"));

        Cipher cipher = Cipher.getInstance(Utils.CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);

        boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
        log.info("Signature " + (isCorrect ? "correct" : "incorrect"));
    }

}
