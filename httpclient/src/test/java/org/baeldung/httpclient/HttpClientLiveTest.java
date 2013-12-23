package org.baeldung.httpclient;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Test(expected = ConnectTimeoutException.class)
    public final void givenLowTimeout_whenExecutingRequestWithTimeout_thenException() throws ClientProtocolException, IOException {
        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(50).setConnectTimeout(50).setSocketTimeout(20).build();
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

    // tests - headers

    @SuppressWarnings("deprecation")
    @Test
    public final void givenDeprecatedApi_whenClientUsesCustomUserAgent_thenCorrect() throws ClientProtocolException, IOException {
        final DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 Firefox/26.0");
        HttpProtocolParams.setUserAgent(client.getParams(), "Mozilla/5.0 Firefox/26.0");

        final HttpGet request = new HttpGet(SAMPLE_URL);
        response = client.execute(request);
    }

    @Test
    public final void givenDeprecatedApi_whenRequestHasCustomUserAgent_thenCorrect() throws ClientProtocolException, IOException {
        instance = HttpClients.custom().build();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Firefox/26.0");
        response = instance.execute(request);
    }

    @Test
    public final void whenRequestHasCustomUserAgent_thenCorrect() throws ClientProtocolException, IOException {
        instance = HttpClients.custom().setUserAgent("Mozilla/5.0 Firefox/26.0").build();
        response = instance.execute(new HttpGet(SAMPLE_URL));
    }

    // tests - cancel request

    @Test
    public final void whenRequestIsCanceled_thenCorrect() throws ClientProtocolException, IOException {
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
