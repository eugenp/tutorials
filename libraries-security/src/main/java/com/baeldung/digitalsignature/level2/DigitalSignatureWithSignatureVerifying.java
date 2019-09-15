package com.baeldung.digitalsignature.level2;

import com.baeldung.digitalsignature.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.Signature;

public class DigitalSignatureWithSignatureVerifying {

    public static void main(String[] args) throws Exception {

        //public key
        PublicKey publicKey = Utils.getPublicKey();

        //received signature
        byte[] sig = Files.readAllBytes(Paths.get("digital_signature_2"));

        Signature signature = Signature.getInstance(Utils.signingAlgorithm);
        signature.initVerify(publicKey);

        //message bytes
        byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

        signature.update(messageBytes);

        boolean isCorrect = signature.verify(sig);
        System.out.println("Signature " + (isCorrect ? "correct" : "incorrect"));

    }
}
