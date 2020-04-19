package com.baeldung.digitalsignature.level1;

import com.baeldung.digitalsignature.Utils;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.PrivateKey;

public class DigitalSignatureWithMessageDigestAndCipherSigning {

    public static void main(String[] args) throws Exception {

        PrivateKey privateKey = Utils.getPrivateKey();

        byte[] messageBytes = Files.readAllBytes(Paths.get("src/test/resources/digitalsignature/message.txt"));

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageHash = md.digest(messageBytes);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] digitalSignature = cipher.doFinal(messageHash);

        Files.write(Paths.get("target/digital_signature_1"), digitalSignature);
    }
}
