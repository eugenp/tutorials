package com.baeldung.security;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.X509EncodedKeySpec;

public class Utils {

    private static final String STORE_TYPE = "PKCS12";
    private static final char[] PASSWORD = "changeit".toCharArray();
    private static final String SENDER_KEYSTORE = "sender_keystore.p12";
    private static final String SENDER_ALIAS = "senderKeyPair";

    public static String signingAlgorithm = "SHA256withRSA";

    private static final String RECEIVER_KEYSTORE = "receiver_keystore.p12";
    private static final String RECEIVER_ALIAS = "receiverKeyPair";

    public static PrivateKey getPrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(STORE_TYPE);
        keyStore.load(new FileInputStream(SENDER_KEYSTORE), PASSWORD);
        return (PrivateKey) keyStore.getKey(SENDER_ALIAS, PASSWORD);
    }

    public static PublicKey getPublicKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(STORE_TYPE);
        keyStore.load(new FileInputStream(RECEIVER_KEYSTORE), PASSWORD);
        Certificate certificate = keyStore.getCertificate(RECEIVER_ALIAS);
        return certificate.getPublicKey();
    }

    public static PublicKey getPublicKey2() throws Exception {
        byte[] publicKeyEncoded = Files.readAllBytes(Paths.get("public_key"));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyEncoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    public static PublicKey getPublicKey3() throws Exception {
        BufferedInputStream bis = null;//...
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate certificate = cf.generateCertificate(bis);
        PublicKey publicKey = certificate.getPublicKey();
        return publicKey;
    }
}
