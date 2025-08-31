package com.baeldung.mtls.calls;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.net.ssl.SSLContext;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SslContextBuilderUnitTest {

    @Test
    public void whenPrivateAndPublicKeysAreGiven_thenAnSSLContextShouldBeCreated()
        throws UnrecoverableKeyException, CertificateException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException,
               KeyManagementException {
        SSLContext sslContext = SslContextBuilder.buildSslContext();
        Assertions.assertThat(sslContext)
            .isNotNull();
    }
}