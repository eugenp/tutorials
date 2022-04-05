package com.baeldung.httpclient.base;

import com.baeldung.httpclient.ResponseUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class HttpClientLiveTest {

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

    // tests

    @Test(expected = ConnectTimeoutException.class)
    public final void givenLowTimeout_whenExecutingRequestWithTimeout_thenException() throws IOException {
        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(50).setConnectTimeout(50).setSocketTimeout(20).build();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.setConfig(requestConfig);
        response = instance.execute(request);
    }

    // tests - configs

    @Test
    public final void givenHttpClientIsConfiguredWithCustomConnectionManager_whenExecutingRequest_thenNoExceptions() throws IOException {
        instance = HttpClientBuilder.create().setConnectionManager(new BasicHttpClientConnectionManager()).build();
        response = instance.execute(new HttpGet(SAMPLE_URL));
    }

    @Test
    public final void givenCustomHeaderIsSet_whenSendingRequest_thenNoExceptions() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.addHeader(HttpHeaders.ACCEPT, "application/xml");
        response = instance.execute(request);
    }

    @Test
    public final void givenRequestWasSet_whenAnalyzingTheHeadersOfTheResponse_thenCorrect() throws IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));

        final Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
        assertThat(headers, not(emptyArray()));
    }

    // tests - cancel request

    @Test
    public final void whenRequestIsCanceled_thenCorrect() throws IOException {
        instance = HttpClients.custom().build();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        response = instance.execute(request);

        try {
            final HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
            }
            System.out.println("----------------------------------------");

            // Do not feel like reading the response body
            // Call abort on the request object
            request.abort();
        } finally {
            response.close();
        }
    }

}
