package com.baeldung.pem;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BouncyCastlePemUtilsUnitTest {

    @Test
    public void whenReadPublicKeyFromPEMFile_thenSuccess() throws Exception {
        File pemFile = new File(BouncyCastlePemUtilsUnitTest.class.getResource("/pem/public-key.pem").getFile());

        RSAPublicKey publicKey1 = BouncyCastlePemUtils.readX509PublicKey(pemFile);
        RSAPublicKey publicKey2 = BouncyCastlePemUtils.readX509PublicKeySecondApproach(pemFile);

        assertEquals("X.509", publicKey1.getFormat());
        assertEquals("RSA", publicKey1.getAlgorithm());

        assertEquals("X.509", publicKey2.getFormat());
        assertEquals("RSA", publicKey2.getAlgorithm());
    }

    @Test
    public void whenReadPrivateKeyFromPEMFile_thenSuccess() throws Exception {
        File pemFile = new File(BouncyCastlePemUtilsUnitTest.class.getResource("/pem/private-key-pkcs8.pem").getFile());

        RSAPrivateKey privateKey1 = BouncyCastlePemUtils.readPKCS8PrivateKey(pemFile);
        RSAPrivateKey privateKey2 = BouncyCastlePemUtils.readPKCS8PrivateKeySecondApproach(pemFile);

        assertEquals("PKCS#8", privateKey1.getFormat());
        assertEquals("RSA", privateKey1.getAlgorithm());

        assertEquals("PKCS#8", privateKey2.getFormat());
        assertEquals("RSA", privateKey2.getAlgorithm());
    }
}
