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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HttpClientBasicPostLiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    private CloseableHttpClient instance;

    private CloseableHttpResponse response;

    @Before
    public final void before() {
        instance = HttpClientBuilder.create().build();
    }

    @After
    public final void after() throws IllegalStateException, IOException {
        ResponseUtil.closeResponse(response);
    }

    // tests - non-GET

    @Test
    public final void whenExecutingPostRequest_thenNoExceptions() throws IOException {
        instance.execute(new HttpPost(SAMPLE_URL));
    }

    @Test
    public final void whenExecutingPostRequestWithBody_thenNoExceptions() throws IOException {
        final HttpPost request = new HttpPost(SAMPLE_URL);
        request.setEntity(new StringEntity("in the body of the POST"));
        instance.execute(request);
    }

    @Test
    public final void givenAuth_whenExecutingPostRequestWithBody_thenNoExceptions() throws IOException, AuthenticationException {
        final HttpPost request = new HttpPost(SAMPLE_URL);
        request.setEntity(new StringEntity("in the body of the POST"));
        final UsernamePasswordCredentials creds = new UsernamePasswordCredentials("username", "password");
        request.addHeader(new BasicScheme().authenticate(creds, request, null));
        instance.execute(request);
    }

}
