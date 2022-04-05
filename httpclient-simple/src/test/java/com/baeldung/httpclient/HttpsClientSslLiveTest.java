package com.baeldung.httpclient;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.junit.Test;

/**
 * This test requires a localhost server over HTTPS <br>
 * It should only be manually run, not part of the automated build
 * */
public class HttpsClientSslLiveTest {

    // "https://localhost:8443/spring-security-rest-basic-auth/api/bars/1" // local
    // "https://mms.nw.ru/" // hosted
    private static final String HOST_WITH_SSL = "https://mms.nw.ru/";

    // tests

    @Test(expected = SSLHandshakeException.class)
    public final void whenHttpsUrlIsConsumed_thenException() throws IOException {
        final CloseableHttpClient httpClient = HttpClientBuilder.create()
            .build();

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenHttpClientPre4_3_whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
        
        final SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslsf).build();
        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        final CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(clientConnectionManager)
                .build();

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));

        httpClient.close();
    }

    @Test
    public final void givenHttpClientAfter4_3_whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
        final SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();

        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        final CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(sslsf)
            .build();

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));

        httpClient.close();
    }

    @Test
    public final void givenHttpClientPost4_3_whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
            .build();
        final NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();

        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        final CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLHostnameVerifier(hostnameVerifier)
            .setSSLSocketFactory(sslsf)
            .build();

        // new

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
        httpClient.close();

    }

    @Test
    public final void givenIgnoringCertificates_whenHttpsUrlIsConsumed_thenCorrect() throws Exception {
        final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true)
            .build();

        final CloseableHttpClient client = HttpClients.custom()
            .setSSLContext(sslContext)
            .setSSLHostnameVerifier(new NoopHostnameVerifier())
            .build();
        final HttpGet httpGet = new HttpGet(HOST_WITH_SSL);
        httpGet.setHeader("Accept", "application/xml");

        final HttpResponse response = client.execute(httpGet);
        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
    }

}
