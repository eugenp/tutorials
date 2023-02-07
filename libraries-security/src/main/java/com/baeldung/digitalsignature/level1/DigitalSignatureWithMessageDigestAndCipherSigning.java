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
import java.security.PrivateKey;

public class DigitalSignatureWithMessageDigestAndCipherSigning {

    public static void main(String[] args) throws Exception {

        PrivateKey privateKey = Utils.getPrivateKey();

        byte[] messageBytes = Files.readAllBytes(Paths.get("src/test/resources/digitalsignature/message.txt"));

        MessageDigest md = MessageDigest.getInstance(Utils.HASHING_ALGORITHM);
        byte[] messageHash = md.digest(messageBytes);

        DigestAlgorithmIdentifierFinder hashAlgorithmFinder  = new DefaultDigestAlgorithmIdentifierFinder();
        AlgorithmIdentifier hashingAlgorithmIdentifier  = hashAlgorithmFinder .find(Utils.HASHING_ALGORITHM);

        DigestInfo digestInfo = new DigestInfo(hashingAlgorithmIdentifier , messageHash);
        byte[] hashToEncrypt = digestInfo.getEncoded();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] digitalSignature = cipher.doFinal(hashToEncrypt);

        Files.write(Paths.get("target/digital_signature_1"), digitalSignature);
    }
}
