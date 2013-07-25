package org.baeldung.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.baeldung.client.spring.ClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RawClientLiveTest {

    @Autowired
    private RestTemplate restTemplate;

    // tests

    @Test
    public final void whenSecuredRestApiIsConsumed_then200OK() throws ClientProtocolException, IOException {
        final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
        final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
        httpClient.getCredentialsProvider().setCredentials(new AuthScope("localhost", 8080, AuthScope.ANY_REALM), new UsernamePasswordCredentials("user", "userPass"));

        final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:8080/spring-security-rest-template/api/foos/1"));
        final int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
    }
}
