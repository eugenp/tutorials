package com.baeldung.digitalsignature.level1;

import com.baeldung.digitalsignature.Utils;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.PrivateKey;

public class DigitalSignatureWithMessageDigestAndCipherSigning {

    public static void main(String[] args) throws Exception {

        //private key
        PrivateKey privateKey = Utils.getPrivateKey();

        //message bytes
        byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

        //message hash (digest, checksum)
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageHash = md.digest(messageBytes);

        //encrypted message hash
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] digitalSignature = cipher.doFinal(messageHash);

        //Save the signature in a file so it can be sent
        Files.write(Paths.get("digital_signature_1"), digitalSignature);
    }
}
