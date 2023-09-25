package com.baeldung.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.baeldung.GetRequestMockServer;

class ClientLiveTest extends GetRequestMockServer {


    @Test
    void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenOk_2() throws GeneralSecurityException {

        final TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
            .register("https", sslsf)
            .register("http", new PlainConnectionSocketFactory())
            .build();

        final BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
        final CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(sslsf)
            .setConnectionManager(connectionManager)
            .build();

        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        final ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(simplePathUrl, HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode().value(), equalTo(200));
    }

    @Test
    void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenCorrect() throws IOException {
        final HttpGet getMethod = new HttpGet(simplePathUrl);

        try (final CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLHostnameVerifier(new NoopHostnameVerifier())
            .build()) {

            final HttpResponse response = httpClient.execute(getMethod);
            assertThat(response.getStatusLine()
                .getStatusCode(), equalTo(200));
        }
    }

    @Test
    void givenAcceptingAllCertificates_whenUsingRestTemplate_thenCorrect() {
        final CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLHostnameVerifier(new NoopHostnameVerifier())
            .build();
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        final ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(simplePathUrl, HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode().value(), equalTo(200));
    }

    @Test
    void whenHttpsUrlIsConsumed_thenException() {
        String urlOverHttps = "https://localhost:"+serverPort+"/httpclient-simple/api/bars/1";
        HttpGet getMethod = new HttpGet(urlOverHttps);

        assertThrows(SSLHandshakeException.class, () -> {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(getMethod);
            assertThat(response.getStatusLine()
                .getStatusCode(), equalTo(200));
        });
    }


}