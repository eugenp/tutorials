package org.baeldung.client;

import static org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * This test requires a localhost server over HTTPS <br>
 * It should only be manually run, not part of the automated build
 * */
public class RestClientLiveManualTest {

    // tests

    @Test
    public final void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenException() throws GeneralSecurityException {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        final CloseableHttpClient httpClient = (CloseableHttpClient) requestFactory.getHttpClient();

        final TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public final boolean isTrusted(final X509Certificate[] certificate, final String authType) {
                return true;
            }
        };
        final SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy, ALLOW_ALL_HOSTNAME_VERIFIER);
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 8443, sf));

        final String urlOverHttps = "https://localhost:8443/spring-security-rest-basic-auth/api/bars/1";
        final ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode().value(), equalTo(200));
    }

}
