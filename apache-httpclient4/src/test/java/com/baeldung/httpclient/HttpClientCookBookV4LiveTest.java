package com.baeldung.httpclient;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

class HttpClientCookBookV4LiveTest {

    private static final String SAMPLE_GET_URL = "http://www.google.com";
    private static final String SAMPLE_POST_URL = "http://www.github.com";

    private CloseableHttpClient client;

    private CloseableHttpResponse response;

    @BeforeEach
    public final void before() {
        client = HttpClientBuilder.create().build();
    }

    @AfterEach
    public final void after() throws IllegalStateException, IOException {
        ResponseUtil.closeResponse(response);
        ClientUtil.closeClient(client);
    }

    @Test
    void givenGetRequestExecuted_thenCorrectStatusCode() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        response = client.execute(httpGet);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    void givenGetRequestExecuted_thenCorrectContentMimeType() throws IOException {
        CloseableHttpResponse response = client.execute(new HttpGet(SAMPLE_GET_URL));
        String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertThat(contentMimeType, equalTo(ContentType.TEXT_HTML.getMimeType()));
    }

    @Test
    void  givenGetRequestExecuted_thenCorrectResponse() throws IOException {
        CloseableHttpResponse response = client.execute(new HttpGet(SAMPLE_GET_URL));
        String bodyAsString = EntityUtils.toString(response.getEntity());
        assertThat(bodyAsString, notNullValue());
    }

    @Test
    void givenLowSocketTimeOut_whenExecutingRequestWithTimeout_thenException() throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(1000)
            .setConnectTimeout(1000)
            .setSocketTimeout(20)
            .build();
        HttpGet request = new HttpGet(SAMPLE_POST_URL);
        request.setConfig(requestConfig);

        assertThrows(SocketTimeoutException.class, () -> {
            response = client.execute(request);
        });
    }

    @Test
    void givenLowSocketTimeOut_whenSettingTimeoutOnTheClient_thenException(){
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(1000)
            .setConnectTimeout(1000)
            .setSocketTimeout(20).build();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig);

        client = builder.build();
        HttpGet request = new HttpGet(SAMPLE_GET_URL);

        assertThrows(SocketTimeoutException.class, () -> {
            response = client.execute(request);
        });
    }

    @Test
    void whenExecutingPostRequest_thenNoExceptions() throws IOException {
        response = client.execute(new HttpPost(SAMPLE_POST_URL));
        assertThat(response.getStatusLine().getStatusCode(), equalTo(301));
    }

    @Test
    void givenParametersAddedToRequest_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_POST_URL);
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key1", "value1"));
        params.add(new BasicNameValuePair("key2", "value2"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        response = client.execute(new HttpPost(SAMPLE_POST_URL));
        assertThat(response.getStatusLine().getStatusCode(), equalTo(301));
    }

    @Test
    void givenRedirectsAreDisabled_whenConsumingUrlWhichRedirects_thenNotRedirected() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
            .disableRedirectHandling()
            .build();
        CloseableHttpResponse response = client.execute(new HttpGet("http://t.co/I5YYd9tddw"));
        assertThat(response.getStatusLine().getStatusCode(), equalTo(301));
    }

    @Test
    void givenHeadersAddedToRequest_thenCorrect() throws IOException {
        HttpGet request = new HttpGet(SAMPLE_GET_URL);
        request.addHeader(HttpHeaders.ACCEPT, "application/xml");
        response = client.execute(request);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    void givenRequestWasSet_whenAnalyzingTheHeadersOfTheResponse_thenCorrect() throws IOException {
        CloseableHttpResponse response = client.execute(new HttpGet(SAMPLE_GET_URL));
        Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
        assertThat(headers, not(emptyArray()));
    }

    @Test
    void givenStreamIsClosed_thenCloseResponse() throws IOException {
        response = client.execute(new HttpGet(SAMPLE_GET_URL));
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                instream.close();
            }
        } finally {
            response.close();
        }
    }

    @Test
    void givenAutoClosableClient_thenCorrect() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // handle response;
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (InputStream instream = entity.getContent()) {
                        // Process the input stream if needed
                    }
                }
                assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
            }
        }
    }

    @Test
    final void whenExecutingPostRequestWithBody_thenNoExceptions() throws IOException {
        final HttpPost request = new HttpPost(SAMPLE_POST_URL);
        request.setEntity(new StringEntity("in the body of the POST"));
        client.execute(request);
    }

    @Test
    final void givenAuth_whenExecutingPostRequestWithBody_thenNoExceptions() throws IOException, AuthenticationException {
        final HttpPost request = new HttpPost(SAMPLE_POST_URL);
        request.setEntity(new StringEntity("in the body of the POST"));
        final UsernamePasswordCredentials creds = new UsernamePasswordCredentials("username", "password");
        request.addHeader(new BasicScheme().authenticate(creds, request, null));
        client.execute(request);
    }

}
