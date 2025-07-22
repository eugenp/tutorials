package com.baeldung.mtls.httpclient;

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

public class HttpClientExample {

    public static void main(String[] args)
        throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, InvalidKeySpecException,
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

    }

}
