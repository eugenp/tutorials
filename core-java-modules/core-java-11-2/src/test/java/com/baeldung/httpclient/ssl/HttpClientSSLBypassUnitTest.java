package com.baeldung.httpclient.ssl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

public class HttpClientSSLBypassUnitTest {

    @Test
    public void givenDisableUsingJVMProperty_whenByPassCertificationVerification_thenSuccessHttpResponse() throws IOException, InterruptedException {
        final Properties props = System.getProperties();
        props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

        HttpClient httpClient = HttpClient.newBuilder()
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://wrong.host.badssl.com/"))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.FALSE.toString());

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void givenMockTrustManager_whenByPassCertificateVerification_thenSuccessHttpResponse() throws IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        SSLContext sslContext = SSLContext.getInstance("SSL"); // OR TLS
        sslContext.init(null, new TrustManager[]{ MOCK_TRUST_MANAGER }, new SecureRandom());
        HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://wrong.host.badssl.com/"))
            .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }


    private static final TrustManager MOCK_TRUST_MANAGER = new X509ExtendedTrustManager() {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) {
        }
    };
}
