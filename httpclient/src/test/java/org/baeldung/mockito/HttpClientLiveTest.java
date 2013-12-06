package org.baeldung.mockito;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpClientLiveTest {

    private static final String SAMPLE_URL = "http://www.google.com";

    private CloseableHttpClient instance;

    private CloseableHttpResponse response;

    @Before
    public final void before() {
        instance = HttpClientBuilder.create().build();
    }

    @After
    public final void after() throws IllegalStateException, IOException {
        if (response == null) {
            return;
        }

        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                final InputStream instream = entity.getContent();
                instream.close();
            }
        } finally {
            response.close();
        }
    }

    // tests

    // simple request - response

    @Test
    public final void whenExecutingBasicGetRequest_thenNoExceptions() throws ClientProtocolException, IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));
    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws ClientProtocolException, IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectMimeType() throws ClientProtocolException, IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));
        final String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();

        assertThat(contentMimeType, equalTo(ContentType.TEXT_HTML.getMimeType()));
    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectBody() throws ClientProtocolException, IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));
        final String bodyAsString = EntityUtils.toString(response.getEntity());

        assertThat(bodyAsString, notNullValue());
    }

    @Test(expected = SocketTimeoutException.class)
    public final void givenLowTimeout_whenExecutingRequestWithTimeout_thenException() throws ClientProtocolException, IOException {
        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(50).setConnectTimeout(50).setSocketTimeout(50).build();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.setConfig(requestConfig);
        response = instance.execute(request);
    }

    // tests - non-GET

    @Test
    public final void whenExecutingBasicRequest_thenNoExceptions() throws ClientProtocolException, IOException {
        instance.execute(new HttpPost(SAMPLE_URL));
    }

    // tests - configs

    @Test
    public final void givenHttpClientIsConfiguredWithCustomConnectionManager_whenExecutingRequest_thenNoExceptions() throws ClientProtocolException, IOException {
        instance = HttpClientBuilder.create().setConnectionManager(new BasicHttpClientConnectionManager()).build();
        response = instance.execute(new HttpGet(SAMPLE_URL));
    }

    @Test
    public final void givenRedirectsAreDisabled_whenConsumingUrlWhichRedirects_thenNotRedirected() throws ClientProtocolException, IOException {
        instance = HttpClientBuilder.create().disableRedirectHandling().build();
        response = instance.execute(new HttpGet("http://t.co/I5YYd9tddw"));
        assertThat(response.getStatusLine().getStatusCode(), equalTo(301));
    }

    @Test
    public final void givenCustomHeaderIsSet_whenSendingRequest_thenNoExceptions() throws ClientProtocolException, IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.addHeader(HttpHeaders.ACCEPT, "application/xml");
        response = instance.execute(request);
    }

    @Test
    public final void givenRequestWasSet_whenAnalyzingTheHeadersOfTheResponse_thenCorrect() throws ClientProtocolException, IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));

        final Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
        assertThat(headers, not(emptyArray()));
    }

}
