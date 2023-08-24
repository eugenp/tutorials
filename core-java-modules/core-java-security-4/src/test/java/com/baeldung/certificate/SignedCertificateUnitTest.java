package com.baeldung.certificate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyStore;
import java.security.SignatureException;
import java.security.cert.X509Certificate;

import static com.baeldung.certificate.RootCertificateUtil.getRootCertificate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignedCertificateUnitTest {

    private KeyStore keyStore;

    private KeyStore trustStore;

    @BeforeEach
    public void setUp() throws Exception {
        char[] passwd = "changeit".toCharArray();
        keyStore = KeyStore.getInstance("JKS");
        keyStore.load(this.getClass().getClassLoader().getResourceAsStream("keystore.jks"), passwd);
        trustStore = KeyStore.getInstance("JKS");
        trustStore.load(this.getClass().getClassLoader().getResourceAsStream("truststore.jks"), passwd);
    }

    @Test
    void whenCertificateIsSelfSigned_thenSubjectIsEqualToIssuer() throws Exception {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("selfsigned");
        assertEquals(certificate.getSubjectDN(), certificate.getIssuerDN());
    }

    @Test
    void whenCertificateIsSelfSigned_thenItCanBeVerifiedWithItsOwnPublicKey() throws Exception {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("selfsigned");
        assertDoesNotThrow(() -> certificate.verify(certificate.getPublicKey()));
    }

    @Test
    void whenCertificateIsCASigned_thenItCantBeVerifiedWithItsOwnPublicKey() throws Exception {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("baeldung");
        assertThrows(SignatureException.class, () -> certificate.verify(certificate.getPublicKey()));
    }

    @Test
    void whenCertificateIsCASigned_thenRootCanBeFoundInTruststore() throws Exception {
        X509Certificate endEntityCertificate = (X509Certificate) keyStore.getCertificate("baeldung");
        X509Certificate rootCertificate = getRootCertificate(endEntityCertificate, trustStore);
        assertNotNull(rootCertificate);
    }

    @Test
    void whenCertificateIsCA_thenItCanBeUsedToSignOtherCertificates() throws Exception {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("cloudflare");
        assertTrue(certificate.getKeyUsage()[5]);
    }

    @Test
    void whenCertificateIsCA_thenBasicConstrainsReturnsZeroOrGreaterThanZero() throws Exception {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("cloudflare");
        assertNotEquals(-1, certificate.getBasicConstraints());
    }

    @Test
    void whenCertificateIsSelfSigned_thenItCantBeUsedToSignOtherCertificates() throws Exception {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("selfsigned");
        assertNull(certificate.getKeyUsage());
    }
}