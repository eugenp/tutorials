package com.baeldung.digitalsignature.level1;

import com.baeldung.digitalsignature.Utils;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;

public class DigitalSignatureWithMessageDigestAndCipherVerifying {

    public static void main(String[] args) throws Exception {

        PublicKey publicKey = Utils.getPublicKey();

        byte[] messageBytes = Files.readAllBytes(Paths.get("src/test/resources/digitalsignature/message.txt"));

        MessageDigest md = MessageDigest.getInstance(Utils.HASHING_ALGORITHM);
        byte[] newMessageHash = md.digest(messageBytes);

        byte[] encryptedMessageHash = Files.readAllBytes(Paths.get("target/digital_signature_1"));

        DigestAlgorithmIdentifierFinder hashAlgorithmFinder = new DefaultDigestAlgorithmIdentifierFinder();
        AlgorithmIdentifier hashingAlgorithmIdentifier = hashAlgorithmFinder.find(Utils.HASHING_ALGORITHM);

        DigestInfo digestInfo = new DigestInfo(hashingAlgorithmIdentifier, newMessageHash);
        byte[] hashToEncrypt = digestInfo.getEncoded();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);

        boolean isCorrect = Arrays.equals(decryptedMessageHash, hashToEncrypt);
        System.out.println("Signature " + (isCorrect ? "correct" : "incorrect"));
    }

}
