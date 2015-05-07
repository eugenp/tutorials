package org.baeldung.httpclient;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
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

    @Test(expected = SSLException.class)
    public final void whenHttpsUrlIsConsumed_thenException() throws ClientProtocolException, IOException {
        final CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @SuppressWarnings("deprecation")
    @Test
    public final void givenHttpClientPre4_3_whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public final boolean isTrusted(final X509Certificate[] certificate, final String authType) {
                return true;
            }
        };
        final SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", 443, sf));
        final ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);

        final CloseableHttpClient httpClient = new DefaultHttpClient(ccm);

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        httpClient.close();
    }

    @Test
    public final void givenHttpClientAfter4_3_whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public final boolean isTrusted(final X509Certificate[] certificate, final String authType) {
                return true;
            }
        };
        final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        final CloseableHttpClient httpClient = HttpClients.custom().setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).setSSLSocketFactory(sslsf).build();

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        httpClient.close();
    }

    @Test
    public final void givenHttpClientPost4_3_whenAcceptingAllCertificates_thenCanConsumeHttpsUriWithSelfSignedCertificate() throws IOException, GeneralSecurityException {
        final SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
        final CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        // new

        final HttpGet getMethod = new HttpGet(HOST_WITH_SSL);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

}
