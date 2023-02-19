package com.baeldung.digitalsignature;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertTrue;

public class DigitalSignatureUnitTest {

    String messagePath = "src/test/resources/digitalsignature/message.txt";
    String senderKeyStore = "src/test/resources/digitalsignature/sender_keystore.jks";
    String receiverKeyStore = "src/test/resources/digitalsignature/receiver_keystore.jks";
    String storeType = "JKS";
    String senderAlias = "senderKeyPair";
    String receiverAlias = "receiverKeyPair";
    char[] password = "changeit".toCharArray();
    String signingAlgorithm = "SHA256withRSA";
    String hashingAlgorithm = "SHA-256";

    @Test
    public void givenMessageData_whenSignWithSignatureSigning_thenVerify() throws Exception {
        PrivateKey privateKey = DigitalSignatureUtils.getPrivateKey(senderKeyStore, password, storeType, senderAlias);
        byte[] messageBytes = Files.readAllBytes(Paths.get(messagePath));

        byte[] digitalSignature = DigitalSignatureUtils.sign(messageBytes, signingAlgorithm, privateKey);

        PublicKey publicKey = DigitalSignatureUtils.getPublicKey(receiverKeyStore, password, storeType, receiverAlias);
        boolean isCorrect = DigitalSignatureUtils.verify(messageBytes, signingAlgorithm, publicKey, digitalSignature);

        assertTrue(isCorrect);
    }

    @Test
    public void givenMessageData_whenSignWithMessageDigestAndCipher_thenVerify() throws Exception {
        PrivateKey privateKey = DigitalSignatureUtils.getPrivateKey(senderKeyStore, password, storeType, senderAlias);
        byte[] messageBytes = Files.readAllBytes(Paths.get(messagePath));

        byte[] encryptedMessageHash = DigitalSignatureUtils.signWithMessageDigestAndCipher(messageBytes, hashingAlgorithm, privateKey);

        PublicKey publicKey = DigitalSignatureUtils.getPublicKey(receiverKeyStore, password, storeType, receiverAlias);
        boolean isCorrect = DigitalSignatureUtils.verifyWithMessageDigestAndCipher(messageBytes, hashingAlgorithm, publicKey, encryptedMessageHash);

        assertTrue(isCorrect);
    }

    @Test
    public void givenMessageData_whenSignWithSignatureSigning_thenVerifyWithMessageDigestAndCipher() throws Exception {
        PrivateKey privateKey = DigitalSignatureUtils.getPrivateKey(senderKeyStore, password, storeType, senderAlias);
        byte[] messageBytes = Files.readAllBytes(Paths.get(messagePath));

        byte[] digitalSignature = DigitalSignatureUtils.sign(messageBytes, signingAlgorithm, privateKey);

        PublicKey publicKey = DigitalSignatureUtils.getPublicKey(receiverKeyStore, password, storeType, receiverAlias);
        boolean isCorrect = DigitalSignatureUtils.verifyWithMessageDigestAndCipher(messageBytes, hashingAlgorithm, publicKey, digitalSignature);

        assertTrue(isCorrect);
    }

    @Test
    public void givenMessageData_whenSignWithMessageDigestAndCipher_thenVerifyWithSignature() throws Exception {
        PrivateKey privateKey = DigitalSignatureUtils.getPrivateKey(senderKeyStore, password, storeType, senderAlias);
        byte[] messageBytes = Files.readAllBytes(Paths.get(messagePath));

        byte[] encryptedMessageHash = DigitalSignatureUtils.signWithMessageDigestAndCipher(messageBytes, hashingAlgorithm, privateKey);

        PublicKey publicKey = DigitalSignatureUtils.getPublicKey(receiverKeyStore, password, storeType, receiverAlias);
        boolean isCorrect = DigitalSignatureUtils.verify(messageBytes, signingAlgorithm, publicKey, encryptedMessageHash);

        assertTrue(isCorrect);
    }

}
