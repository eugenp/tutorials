package com.baeldung.bouncycastle;

import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SignCSRBouncyCastleUnitTest {

    @Test
    public void givenCSR_whenSignWithBC_thenSuccess() throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, IOException, NoSuchProviderException, SignatureException, InvalidKeyException {
        SignCSRBouncyCastle signCSRBouncyCastle = new SignCSRBouncyCastle();
        KeyPair pair = SignCSRBouncyCastle.generateRSAKeyPair();
        PKCS10CertificationRequest csr = SignCSRBouncyCastle.generateCSR(pair);
        KeyPair caPair = SignCSRBouncyCastle.generateRSAKeyPair();
        X509Certificate signedCert = signCSRBouncyCastle.signCSR(csr, caPair.getPrivate(), pair);

        assertThat(signedCert).isNotNull();
        assertThat(signedCert.getSubjectDN().getName()).isEqualTo("CN=Requested Test Certificate");
        assertDoesNotThrow(() -> signedCert.verify(caPair.getPublic()));
    }

}
