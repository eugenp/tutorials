package com.baeldung.mtls.calls;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MutualTLSCallWithHttpURLConnectionLiveTest {

    @Test
    public void whenWeExecuteMutualTLSCallToNginxServerWithHttpURLConnection_thenItShouldReturnNonNullResponse()
        throws UnrecoverableKeyException, CertificateException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException,
               KeyManagementException {
        SSLContext sslContext = SslContextBuilder.buildSslContext();
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://127.0.0.1/ping").openConnection();
        httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
        httpsURLConnection.setHostnameVerifier(HostNameVerifierBuilder.getAllHostsValid());
        InputStream inputStream = httpsURLConnection.getInputStream();
        String response = new String(inputStream.readAllBytes(), Charset.defaultCharset());
        Assertions.assertThat(response)
            .isNotNull();
    }

}
