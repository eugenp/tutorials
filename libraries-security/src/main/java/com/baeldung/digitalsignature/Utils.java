package com.baeldung.digitalsignature;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class Utils {

    private static final String STORE_TYPE = "PKCS12";
    private static final char[] PASSWORD = "changeit".toCharArray();
    private static final String SENDER_KEYSTORE = "sender_keystore.p12";
    private static final String SENDER_ALIAS = "senderKeyPair";

    public static final String SIGNING_ALGORITHM = "SHA256withRSA";

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
}
