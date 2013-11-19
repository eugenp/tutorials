package org.baeldung.mockito;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class HttpClientLiveTest {

    private static final String SAMPLE_URL = "http://www.google.com";

    private HttpClient instance;

    @Before
    public final void before() {
        instance = HttpClientBuilder.create().build();
    }

    // tests

    @Test
    public final void whenExecutingBasicGetRequest_thenNoExceptions() throws ClientProtocolException, IOException {
        instance.execute(new HttpGet(SAMPLE_URL));
    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws ClientProtocolException, IOException {
        final HttpResponse response = instance.execute(new HttpGet(SAMPLE_URL));
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectMimeType() throws ClientProtocolException, IOException {
        final HttpResponse response = instance.execute(new HttpGet(SAMPLE_URL));
        final String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();

        assertThat(contentMimeType, equalTo(ContentType.TEXT_HTML.getMimeType()));
    }

    @Test(expected = SocketTimeoutException.class)
    public final void givenLowTimeout_whenExecutingRequestWithTimeout_thenException() throws ClientProtocolException, IOException {
        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(50).setConnectTimeout(50).setSocketTimeout(50).build();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.setConfig(requestConfig);
        instance.execute(request);
    }

    @Test
    public final void givenHttpClientIsConfiguredWithCustomConnectionManager_whenExecutingRequest_thenNoExceptions() throws ClientProtocolException, IOException {
        instance = HttpClientBuilder.create().setConnectionManager(new BasicHttpClientConnectionManager()).build();
        instance.execute(new HttpGet(SAMPLE_URL));
    }

}
