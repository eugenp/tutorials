package com.baeldung.httpclient.sec;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.apache.commons.codec.binary.Base64;

import com.baeldung.handler.CustomHttpClientResponseHandler;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.protocol.HttpContext;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 * NOTE : Need module httpclient-simple to be running
 */
class HttpClientAuthLiveTest {

    private static final String URL_SECURED_BY_BASIC_AUTHENTICATION = "http://localhost:8082/httpclient-simple/api/foos/1";
    private static final String DEFAULT_USER = "user1";
    private static final String DEFAULT_PASS = "user1Pass";
    private final char[] DEFAULT_PASS_ARRAY = DEFAULT_PASS.toCharArray() ;

    @Test
    final void whenExecutingBasicGetRequestWithBasicAuthenticationEnabled_thenSuccess() throws IOException {
        final HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
        try (CloseableHttpClient client = HttpClientBuilder.create()
            .setDefaultCredentialsProvider(provider())
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {
            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    final void givenAuthenticationIsPreemptive_whenExecutingBasicGetRequestWithBasicAuthenticationEnabled_thenSuccess() throws IOException {
        final HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
        try (CloseableHttpClient client = HttpClientBuilder.create()
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, context(), new CustomHttpClientResponseHandler())) {
            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(200));
        }
    }

    @Test
    final void givenAuthorizationHeaderIsSetManually_whenExecutingGetRequest_thenSuccess() throws IOException {
        final HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
        request.setHeader(HttpHeaders.AUTHORIZATION, authorizationHeader(DEFAULT_USER, DEFAULT_PASS));
        try (CloseableHttpClient client = HttpClientBuilder.create()
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, context(), new CustomHttpClientResponseHandler())) {
            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    final void givenAuthorizationHeaderIsSetManually_whenExecutingGetRequest_thenSuccess2() throws IOException {
        final HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
        final String auth = DEFAULT_USER + ":" + DEFAULT_PASS;
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        final String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        try (CloseableHttpClient client = HttpClientBuilder.create()
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {
            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    // UTILS

    private CredentialsProvider provider() {
        final HttpHost targetHost = new HttpHost("http", "localhost", 8082);
        final BasicCredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(targetHost);
        provider.setCredentials(authScope, new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS_ARRAY));
        return provider;
    }

    private HttpContext context() {
        final HttpHost targetHost = new HttpHost("http", "localhost", 8082);
        final BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(targetHost);
        credsProvider.setCredentials(authScope, new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS_ARRAY));

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
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));

        return "Basic " + new String(encodedAuth);
    }

}
