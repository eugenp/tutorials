package com.baeldung.httpclient.httpclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class HttpClientCookBookLiveTest {

    private static final String SAMPLE_GET_URL = "http://www.google.com";
    private static final String SAMPLE_POST_URL = "http://www.github.com";

    private CloseableHttpClient httpClient;

    private CloseableHttpResponse response;

    @BeforeEach
    public final void before() {
        httpClient = HttpClientBuilder.create().build();
    }

    @AfterEach
    public final void after() throws IOException {
       ClientUtil.closeClient(httpClient);
    }

    @Test
    void givenGetRequestExecuted_thenCorrectStatusCode() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        httpClient.execute(httpGet,
            response -> {
                assertThat(response.getCode()).isEqualTo(200);
                return response;
            }
        );
    }

    @Test
    void givenGetRequestExecuted_thenCorrectContentMimeType() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        httpClient.execute(httpGet,
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
        httpClient.execute(httpGet,
            response -> {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                assertThat(bodyAsString, notNullValue());
                return response;
            }
        );
    }

    @Test
    void whenConfigureTimeoutOnRequest_thenCorrectResponse() throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(2000L))
            .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .build();

        HttpGet request = new HttpGet(SAMPLE_GET_URL);
        request.setConfig(requestConfig);

        httpClient.execute(request,
            response -> {
                assertThat(response.getCode()).isEqualTo(200);
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
            httpClient.execute(request, resp -> resp);
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
        httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.defaultCharset()));

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
        client.execute(new HttpGet("http://t.co/I5YYd9tddw"), response -> {
            assertThat(response.getCode()).isEqualTo(301);
            return response;
        });
    }

    @Test
    void givenHeadersAddedToRequest_thenCorrect() throws IOException {
        HttpGet request = new HttpGet(SAMPLE_GET_URL);
        request.addHeader(HttpHeaders.ACCEPT, "application/xml");
        httpClient.execute(request,
            response -> {
                assertThat(response.getCode()).isEqualTo(200);
                return response;
            }
        );
    }

    @Test
    void givenRequestWasSet_whenAnalyzingTheHeadersOfTheResponse_thenCorrect() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        httpClient.execute(httpGet,
            response -> {
                Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
                assertThat(headers, not(emptyArray()));
                return response;
            }
        );
    }

    @Test
    void givenAutoClosableClient_thenCorrect() throws IOException {
        HttpGet httpGet = new HttpGet(SAMPLE_GET_URL);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            httpClient.execute(httpGet, resp -> {
                assertThat(resp.getCode()).isEqualTo(200);
                return resp;
            });
        }
    }

}
