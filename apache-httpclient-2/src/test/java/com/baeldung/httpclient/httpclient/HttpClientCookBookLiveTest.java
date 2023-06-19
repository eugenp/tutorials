package com.baeldung.httpclient.httpclient;


import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.core5.http.ConnectionRequestTimeoutException;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.Consts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;


class HttpClientCookBookLiveTest {

    private static final String SAMPLE_GET_URL = "http://www.google.com";
    private static final String SAMPLE_POST_URL = "http://www.github.com";

    private CloseableHttpClient httpClient;

    private CloseableHttpResponse response;

    @BeforeEach
    public final void before() {
        httpClient = HttpClientBuilder.create().build();
    }


    @Test
    void givenGetRequestExecuted_thenCorrectStatusCode() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        ClassicHttpResponse classicHttpResponse = httpClient.execute(httpGet,
            response -> {
                assertThat(response.getCode()).isEqualTo(200);
                return response;
            }
        );
    }


    @Test
    void givenGetRequestExecuted_thenCorrectContentMimeType() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        ClassicHttpResponse classicHttpResponse = httpClient.execute(httpGet,
            response -> {
                final String contentMimeType = ContentType.parse(response.getEntity().getContentType()).getMimeType();
                assertThat(contentMimeType).isEqualTo(ContentType.TEXT_HTML.getMimeType());
                return response;
            }
        );
    }


    @Test
    void  givenGetRequestExecuted_thenCorrectResponse() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        ClassicHttpResponse classicHttpResponse = httpClient.execute(httpGet,
            response -> {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                assertThat(bodyAsString, notNullValue());
                return response;
            }
        );
    }


    @Test
    void givenLowSocketTimeOut_whenExecutingRequestWithTimeout_thenException() throws IOException {
        ConnectionConfig connConfig = ConnectionConfig.custom()
            .setConnectTimeout(1000, TimeUnit.MILLISECONDS)
            .setSocketTimeout(20, TimeUnit.MILLISECONDS)
            .build();

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(2000L))
            .build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(cm)
            .build();

        HttpGet request = new HttpGet(SAMPLE_GET_URL);

        assertThrows(SocketTimeoutException.class, () -> {
            httpClient.execute(request, resp -> {
                return resp;
            });
        });
    }


    @Test
    void whenExecutingPostRequest_thenNoExceptions() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_POST_URL);
        httpClient.execute(httpPost,
            response -> {
                assertThat(response.getCode()).isEqualTo(200);
                return response;
            }
        );
    }

    @Test
    void givenParametersAddedToRequest_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_POST_URL);
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key1", "value1"));
        params.add(new BasicNameValuePair("key2", "value2"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        httpClient.execute(httpPost, response -> {
            assertThat(response.getCode()).isEqualTo(200);
            return response;
        });
    }

    @Test
    void givenRedirectsAreDisabled_whenConsumingUrlWhichRedirects_thenNotRedirected() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
            .disableRedirectHandling()
            .build();
        ClassicHttpResponse classicHttpResponse = client.execute(new HttpGet("http://t.co/I5YYd9tddw"), response -> {
            assertThat(response.getCode()).isEqualTo(301);
            return response;
        });
    }

    @Test
    void givenHeadersAddedToRequest_thenCorrect() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        httpGet.addHeader(HttpHeaders.ACCEPT, "application/xml");
        ClassicHttpResponse classicHttpResponse = httpClient.execute(httpGet,
            response -> {
                assertThat(response.getCode()).isEqualTo(200);
                return response;
            }
        );
    }

    @Test
    void givenRequestWasSet_whenAnalyzingTheHeadersOfTheResponse_thenCorrect() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        ClassicHttpResponse classicHttpResponse = httpClient.execute(httpGet,
            response -> {
                Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
                assertThat(headers, not(emptyArray()));
                return response;
            }
        );
    }

    @Test
    void givenStreamIsClosed_thenCloseResponse() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        response = httpClient.execute(httpGet);
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
}
