package com.baeldung.security.level2;

import com.baeldung.security.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Signature;

public class DigitalSignatureWithSignatureSigning {

    public static void main(String[] args) throws Exception {

        //private key
        PrivateKey privateKey = Utils.getPrivateKey();


        Signature signature = Signature.getInstance(Utils.signingAlgorithm);
        signature.initSign(privateKey);

        //message bytes
        byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

        signature.update(messageBytes);
        byte[] digitalSignature = signature.sign();

        //Save the signature in a file so it can be sent
        Files.write(Paths.get("digital_signature_2"), digitalSignature);

    }

}
