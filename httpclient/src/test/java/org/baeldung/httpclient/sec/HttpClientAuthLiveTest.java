package org.baeldung.httpclient.sec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.baeldung.httpclient.ResponseUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/*
 * NOTE : Need module spring-security-rest-basic-auth to be running
 */

public class HttpClientAuthLiveTest {

    private static final String URL_SECURED_BY_BASIC_AUTHENTICATION = "http://localhost:8081/spring-security-rest-basic-auth/api/foos/1";
    private static final String DEFAULT_USER = "user1";
    private static final String DEFAULT_PASS = "user1Pass";

    private CloseableHttpClient client;

    private CloseableHttpResponse response;

    @Before
    public final void before() {
        client = HttpClientBuilder.create().build();
    }

    @After
    public final void after() throws IllegalStateException, IOException {
        ResponseUtil.closeResponse(response);
    }

    // tests

    @Test
    public final void whenExecutingBasicGetRequestWithBasicAuthenticationEnabled_thenSuccess() throws IOException {
        client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider()).build();

        response = client.execute(new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION));

        final int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    }

    @Test
    public final void givenAuthenticationIsPreemptive_whenExecutingBasicGetRequestWithBasicAuthenticationEnabled_thenSuccess() throws IOException {
        client = HttpClientBuilder.create().build();
        response = client.execute(new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION), context());

        final int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    }

    @Test
    public final void givenAuthorizationHeaderIsSetManually_whenExecutingGetRequest_thenSuccess() throws IOException {
        client = HttpClientBuilder.create().build();

        final HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
        request.setHeader(HttpHeaders.AUTHORIZATION, authorizationHeader(DEFAULT_USER, DEFAULT_PASS));
        response = client.execute(request);

        final int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    }

    @Test
    public final void givenAuthorizationHeaderIsSetManually_whenExecutingGetRequest_thenSuccess2() throws IOException {
        final HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
        final String auth = DEFAULT_USER + ":" + DEFAULT_PASS;
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        client = HttpClientBuilder.create().build();
        response = client.execute(request);

        final int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    }

    // UTILS

    private CredentialsProvider provider() {
        final CredentialsProvider provider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS);
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }

    private HttpContext context() {
        final HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS));

        // Create AuthCache instance
        final AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        authCache.put(targetHost, new BasicScheme());

        // Add AuthCache to the execution context
        final HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        return context;
    }

    private String authorizationHeader(final String username, final String password) {
        final String auth = username + ":" + password;
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));

        return "Basic " + new String(encodedAuth);
    }

}
