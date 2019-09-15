package com.baeldung.security.level1;

import com.baeldung.security.Utils;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;

public class DigitalSignatureWithMessageDigestAndCipherVerifying {

    public static void main(String[] args) throws Exception {

        //public key
        PublicKey publicKey = Utils.getPublicKey();

        //message bytes
        byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

        //message hash (digest, checksum)
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] newMessageHash = md.digest(messageBytes);

        //received signature
        byte[] encryptedMessageHash = Files.readAllBytes(Paths.get("digital_signature_1"));

        //decrypted received signature
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);

        //compare messageHash && decryptedMessageHash
        boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
        System.out.println("Signature " + (isCorrect ? "correct" : "incorrect"));

    }

}
