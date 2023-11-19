package com.baeldung.client;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.baeldung.handler.CustomHttpClientResponseHandler;


/**
 * This test requires a localhost server over HTTPS <br>
 * It should only be manually run, not part of the automated build
 * */
class RestClientLiveManualTest {

    final String urlOverHttps = "http://localhost:8082/httpclient-simple/api/bars/1";


    // new httpClient : 4.4 and above
    @Test
    void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenOk() throws GeneralSecurityException {

        final TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
            .register("https", sslsf)
            .register("http", new PlainConnectionSocketFactory())
            .build();

        final BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
        final CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build();

        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        final ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode().value(), equalTo(200));
    }



    @Test
    void whenHttpsUrlIsConsumed_thenException() {
        String urlOverHttps = "https://localhost:8082/httpclient-simple";
        HttpGet getMethod = new HttpGet(urlOverHttps);
        assertThrows(SSLPeerUnverifiedException.class, () -> {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(getMethod, new CustomHttpClientResponseHandler());
            assertThat(response.getCode(), equalTo(200));
        });
    }
}
