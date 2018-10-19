package org.baeldung.httpclient.base;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.baeldung.httpclient.ResponseUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class HttpClientBasicLiveTest {

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

    // simple request - response

    @Test
    public final void whenExecutingBasicGetRequest_thenNoExceptions() throws ClientProtocolException, IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));
    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws ClientProtocolException, IOException {
        response = instance.execute(new HttpGet(SAMPLE_URL));
        final int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
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

}
