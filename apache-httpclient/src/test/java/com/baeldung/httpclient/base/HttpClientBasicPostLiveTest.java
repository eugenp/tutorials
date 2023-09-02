package com.baeldung.httpclient.base;

import com.baeldung.httpclient.ResponseUtil;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class HttpClientBasicPostLiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    private CloseableHttpClient instance;

    private CloseableHttpResponse response;

    @BeforeEach
    public final void beforeEach() {
        instance = HttpClientBuilder.create().build();
    }

    @AfterEach
    public final void afterEach() throws IllegalStateException, IOException {
        ResponseUtil.closeResponse(response);
    }

    // tests - non-GET

    @Test
    final void whenExecutingPostRequest_thenNoExceptions() throws IOException {
        instance.execute(new HttpPost(SAMPLE_URL));
    }

    @Test
    final void whenExecutingPostRequestWithBody_thenNoExceptions() throws IOException {
        final HttpPost request = new HttpPost(SAMPLE_URL);
        request.setEntity(new StringEntity("in the body of the POST"));
        instance.execute(request);
    }

    @Test
    final void givenAuth_whenExecutingPostRequestWithBody_thenNoExceptions() throws IOException, AuthenticationException {
        final HttpPost request = new HttpPost(SAMPLE_URL);
        request.setEntity(new StringEntity("in the body of the POST"));
        final UsernamePasswordCredentials creds = new UsernamePasswordCredentials("username", "password");
        request.addHeader(new BasicScheme().authenticate(creds, request, null));
        instance.execute(request);
    }

}
