package com.baeldung.javafeatures.mlkemdsa;

import java.security.spec.NamedParameterSpec;
import javax.crypto.KEM;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleLatticeBasedDigitalSignature {

    public static void main(String[] args) throws Exception {
        Logger logger = LoggerFactory.getLogger(ModuleLatticeBasedDigitalSignature.class);

        // Signer: Generate an ML-DSA key pair
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("ML-DSA");
        kpg.initialize(NamedParameterSpec.ML_DSA_65);

        KeyPair kp = kpg.generateKeyPair();
        PrivateKey privateKey = kp.getPrivate();
        PublicKey publicKey = kp.getPublic();

        // Signer: Sign the message using the private key
        Signature signature = Signature.getInstance("ML-DSA");
        String message = "This is a test message signed";
        byte[] messageBytes = message.getBytes();

        signature.initSign(privateKey);
        signature.update(messageBytes);
        byte[] sigBytes = signature.sign();

        // Verifier: Verify the signature using the public key
        signature.initVerify(publicKey);
        signature.update(messageBytes);
        boolean isValid = signature.verify(sigBytes);

        logger.info("Is the signature valid? " + isValid);

    }
}
