package org.baeldung.client;

import static org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.baeldung.client.spring.ClientConfig;
import org.baeldung.web.dto.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ClientLiveTest {

    @Autowired
    private RestTemplate secureRestTemplate;

    // tests

    @Test
    public final void whenSecuredRestApiIsConsumed_then200OK() {
        final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) secureRestTemplate.getRequestFactory();
        final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
        httpClient.getCredentialsProvider().setCredentials(new AuthScope("localhost", 8080, AuthScope.ANY_REALM), new UsernamePasswordCredentials("user", "userPass"));

        final ResponseEntity<Foo> responseEntity = secureRestTemplate.exchange("http://localhost:8080/spring-security-rest-basic-auth/api/foos/1", HttpMethod.GET, null, Foo.class);
        System.out.println(responseEntity.getStatusCode());
    }

    @Test(expected = ResourceAccessException.class)
    public final void whenHttpsUrlIsConsumed_thenException() {
        final String urlOverHttps = "https://localhost:8443/spring-security-rest-basic-auth/api/bars/1";
        final ResponseEntity<String> response = new RestTemplate().exchange(urlOverHttps, HttpMethod.GET, null, String.class);
        assertThat(response.getStatusCode().value(), equalTo(200));
    }

    @Test
    public final void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenException() throws GeneralSecurityException {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();

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
