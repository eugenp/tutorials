package com.baeldung.httpclient.base;

import com.baeldung.handler.CustomHttpClientResponseHandler;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class HttpClientBasicLiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    @Test
    public final void whenExecutingBasicGetRequest_thenNoExceptions() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);

        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {
        }
    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
        }
    }

//    @Test
//    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectMimeType() throws ClientProtocolException, IOException {
//        response = instance.execute(new HttpGet(SAMPLE_URL));
//        final String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
//
//        assertThat(contentMimeType, equalTo(ContentType.TEXT_HTML.getMimeType()));
//    }

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectContentType() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);

        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            String contentType = response.getEntity().getContentType();
            assertThat(contentType, equalTo("text/html; charset=utf-8"));
        }
    }


    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectBody() throws IOException, ParseException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            final String bodyAsString = EntityUtils.toString(response.getEntity());
            assertThat(bodyAsString, notNullValue());
        }
    }

}
