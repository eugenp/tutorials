package com.baeldung.kem;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KemUtilsIntegrationTest {
    private static KeyPair keyPair;
    public static final String KEM_ALGORITHM = "DHKEM";


    @BeforeAll
    static void setup() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("X25519");
        keyPair = kpg.generateKeyPair();
    }

    @Test
    void givenKem_whenSenderEncapsulatesAndReceiverDecapsulates_thenSecretsMatch() throws Exception {
        KemUtils.KemResult senderResult = KemUtils.encapsulate(KEM_ALGORITHM, keyPair.getPublic());
        assertNotNull(senderResult.sharedSecret());
        assertNotNull(senderResult.encapsulation());

        KemUtils.KemResult receiverResult = KemUtils.decapsulate(KEM_ALGORITHM, keyPair.getPrivate(), senderResult.encapsulation());

        SecretKey senderSecret = senderResult.sharedSecret();
        SecretKey receiverSecret = receiverResult.sharedSecret();

        assertArrayEquals(senderSecret.getEncoded(), receiverSecret.getEncoded(),
            "Shared secrets from sender and receiver must match");
    }

    @Test
    void givenDifferentReceiverKey_whenDecapsulate_thenFails() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        KeyPair wrongKeyPair = kpg.generateKeyPair();

        KemUtils.KemResult senderResult = KemUtils.encapsulate(KEM_ALGORITHM, keyPair.getPublic());

        assertThrows(Exception.class, () ->
            KemUtils.decapsulate(KEM_ALGORITHM, wrongKeyPair.getPrivate(), senderResult.encapsulation()));
    }

}
