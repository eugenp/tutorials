package com.baeldung.digitalsignature.level2;

import com.baeldung.digitalsignature.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Signature;

public class DigitalSignatureWithSignatureSigning {

    public static void main(String[] args) throws Exception {

        PrivateKey privateKey = Utils.getPrivateKey();

        Signature signature = Signature.getInstance(Utils.SIGNING_ALGORITHM);
        signature.initSign(privateKey);

        byte[] messageBytes = Files.readAllBytes(Paths.get("src/test/resources/digitalsignature/message.txt"));

        signature.update(messageBytes);
        byte[] digitalSignature = signature.sign();

        Files.write(Paths.get("target/digital_signature_2"), digitalSignature);
    }

}
