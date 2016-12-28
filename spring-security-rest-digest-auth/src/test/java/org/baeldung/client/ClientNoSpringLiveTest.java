package org.baeldung.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.baeldung.web.dto.Foo;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ClientNoSpringLiveTest {
    private static final String DEFAULT_USER = "user1";
    private static final String DEFAULT_PASS = "user1Pass";

    // tests - no Spring

    @Test
    public final void givenUsingCustomHttpRequestFactory_whenSecuredRestApiIsConsumed_then200OK() {
        final HttpHost host = new HttpHost("localhost", 8082, "http");

        final CredentialsProvider credentialsProvider = provider();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).useSystemProperties().build();
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactoryDigestAuth(host, client);
        final RestTemplate restTemplate = new RestTemplate(requestFactory);

        // credentialsProvider.setCredentials(new AuthScope("localhost", 8082, AuthScope.ANY_REALM), new UsernamePasswordCredentials("user1", "user1Pass"));

        final String uri = "http://localhost:8082/spring-security-rest-digest-auth/api/foos/1";
        final ResponseEntity<Foo> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, Foo.class);

        System.out.println(responseEntity.getStatusCode());
    }

    @Test
    public final void givenUsingStandardRequestFactory_whenSecuredRestApiIsConsumed_then200OK() {
        final CredentialsProvider credentialsProvider = provider();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).useSystemProperties().build();
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(client);
        final RestTemplate restTemplate = new RestTemplate(requestFactory);

        // credentialsProvider.setCredentials(new AuthScope("localhost", 8080, AuthScope.ANY_REALM), new UsernamePasswordCredentials("user1", "user1Pass"));

        final String uri = "http://localhost:8082/spring-security-rest-digest-auth/api/foos/1";
        final ResponseEntity<Foo> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, Foo.class);

        System.out.println(responseEntity.getStatusCode());
    }

    // UTIL

    private final CredentialsProvider provider() {
        final CredentialsProvider provider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS);
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }

}
