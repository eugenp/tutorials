package com.baeldung.mtls.calls;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.net.ssl.SSLContext;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MutualTLSCallWithHttpClientLiveTest {

    @Test
    public void whenWeExecuteMutualTLSCallToNginxServerWithHttpClient_thenItShouldReturnStatusOK()
        throws UnrecoverableKeyException, CertificateException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException,
               KeyManagementException {
        SSLContext sslContext = SslContextBuilder.buildSslContext();
        HttpClient client = HttpClient.newBuilder()
            .sslContext(sslContext)
            .build();

        HttpRequest exactRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://localhost/ping"))
            .GET()
            .build();

        HttpResponse<String> response = client.sendAsync(exactRequest, HttpResponse.BodyHandlers.ofString())
            .join();
        Assertions.assertThat(response)
            .isNotNull();
        Assertions.assertThat(response.statusCode())
            .isEqualTo(200);
    }

}
