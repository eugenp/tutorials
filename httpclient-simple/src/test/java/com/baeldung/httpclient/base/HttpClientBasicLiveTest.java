package com.baeldung.httpclient.base;

import com.baeldung.handler.CustomHttpClientResponseHandler;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


class HttpClientBasicLiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    @Test
    void whenExecutingBasicGetRequest_thenNoExceptions() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);

        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {
        }
    }

    @Test
    void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
        }
    }


    @Test
    void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectMimeType() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);

        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            final String contentMimeType = ContentType.parse(response.getEntity().getContentType()).getMimeType();
            assertThat(contentMimeType, equalTo(ContentType.TEXT_HTML.getMimeType()));
        }
    }


    @Test
    void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectBody() throws IOException, ParseException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        try (CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            assertThat(response, notNullValue());
        }
    }

}
