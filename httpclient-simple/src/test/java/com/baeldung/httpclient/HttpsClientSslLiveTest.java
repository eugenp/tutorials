package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;

import com.baeldung.handler.CustomHttpClientResponseHandler;

/**
 * This test requires a localhost server over HTTPS <br>
 * It should only be manually run, not part of the automated build
 * */
class HttpsClientSslLiveTest {

    // "https://localhost:8443/spring-security-rest-basic-auth/api/bars/1" // local
    // "https://mms.nw.ru/" // hosted
    private static final String HOST_WITH_SSL = "https://mms.nw.ru/";

    // tests

    @Test
    void whenHttpsUrlIsConsumed_thenException()  {

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);

        assertThrows(SSLHandshakeException.class, () -> {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(getMethod, new CustomHttpClientResponseHandler());
            assertThat(response.getCode(), Matchers.equalTo(200));
        });
    }

    @Test
    void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenOk() throws GeneralSecurityException, IOException {

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);

        final TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        final SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();
        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
            .register("https", sslsf)
            .register("http", new PlainConnectionSocketFactory())
            .build();

        final BasicHttpClientConnectionManager connectionManager =
            new BasicHttpClientConnectionManager(socketFactoryRegistry);

        try( CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) httpClient
                .execute(getMethod, new CustomHttpClientResponseHandler())) {

                final int statusCode = response.getCode();
                assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }


    @Test
    void whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
        final SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();

        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslsf)
            .build();

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);

        try (CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(cm)
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(getMethod, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, Matchers.equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void usingBuilder_whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final SSLContext sslContext = new SSLContextBuilder()
            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
            .build();
        final NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();

        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslsf)
            .build();

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);

        try (CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(cm)
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(getMethod, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, Matchers.equalTo(HttpStatus.SC_OK));
        }
    }

}
