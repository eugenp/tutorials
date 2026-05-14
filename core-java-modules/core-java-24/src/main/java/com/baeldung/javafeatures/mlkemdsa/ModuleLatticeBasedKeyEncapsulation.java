package com.baeldung.javafeatures.mlkemdsa;

import java.security.spec.NamedParameterSpec;
import javax.crypto.KEM;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleLatticeBasedKeyEncapsulation {

    public static void main(String[] args) throws Exception {
        Logger logger = LoggerFactory.getLogger(ModuleLatticeBasedKeyEncapsulation.class);

        // Receiver: Generate an ML-KEM key pair
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("ML-KEM");
        kpg.initialize(NamedParameterSpec.ML_KEM_768);

        KeyPair receiverKeyPair = kpg.generateKeyPair();
        PrivateKey privateKey = receiverKeyPair.getPrivate();
        PublicKey publicKey = receiverKeyPair.getPublic();

        // Simulated transmission of the public key to the sender

        // Sender: Encapsulate a shared secret key using the receiver's public key
        KEM senderKem = KEM.getInstance("ML-KEM");
        KEM.Encapsulator encapsulator = senderKem.newEncapsulator(publicKey);
        KEM.Encapsulated encapsulated = encapsulator.encapsulate();
        SecretKey senderSharedSecret = encapsulated.key();
        byte[] ciphertext = encapsulated.encapsulation();

        // Simulated transmission of the secret key to the receiver

        // Receiver: Decapsulate the secret key using the private key
        KEM receiverKem = KEM.getInstance("ML-KEM");
        KEM.Decapsulator decapsulator = receiverKem.newDecapsulator(privateKey);
        SecretKey receiverSharedSecret = decapsulator.decapsulate(ciphertext);

        // Verify that both parties have the same secret key
        boolean match = Arrays.equals(senderSharedSecret.getEncoded(), receiverSharedSecret.getEncoded());
        logger.info("Do the shared secret keys match? " + match);

    }
}
