package com.baeldung.multiple_truststores;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.junit.jupiter.api.Test;

import com.baeldung.multiple_truststores.SslContextConfigurer;
import sun.security.x509.X509CertImpl;

public class SslContextConfigurerUnitTest {

    @Test
    public void givenCustomTrustManager_whenCheckingCertificateValidity_thenTrustManagerApprovesCert()
      throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        X509TrustManager trustManager = SslContextConfigurer.addAdditionalTrustStore(
          "new_trustStore.p12",
          "change"
        );

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("cert_to_check.pem");
        X509CertImpl x509Cert = new X509CertImpl(resourceAsStream);
        trustManager.checkClientTrusted(new X509Certificate[]{x509Cert}, "server");
    }
}