package com.baeldung.httpclient;

import com.google.common.collect.Lists;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.message.BasicHeader;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.List;

class HttpClientHeadersLiveTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SAMPLE_URL = "http://www.github.com";

    @Test
    void whenClientUsesCustomUserAgent_thenCorrect() throws IOException {
        final CloseableHttpClient client = HttpClients.custom()
            .setUserAgent("Mozilla/5.0 Firefox/26.0")
            .build();
        final HttpGet request = new HttpGet(SAMPLE_URL);

        String response = client.execute(request, new BasicHttpClientResponseHandler());
        logger.info("Response -> {}", response);
    }

    @Test
    void whenRequestHasCustomUserAgent_thenCorrect() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Firefox/26.0");
        String response = client.execute(request, new BasicHttpClientResponseHandler());
        logger.info("Response -> {}", response);
    }

    @Test
    void whenRequestHasCustomContentType_thenCorrect() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String response = client.execute(request, new BasicHttpClientResponseHandler());
            logger.debug("Response -> {}", response);
        }
    }

    @Test
    void givenConfigOnClient_whenRequestHasCustomContentType_thenCorrect() throws IOException {
        final HttpGet request = new HttpGet(SAMPLE_URL);
        final Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        final List<Header> headers = Lists.newArrayList(header);


        try (CloseableHttpClient client = HttpClients.custom()
            .setDefaultHeaders(headers)
            .build()) {

            String response = client.execute(request, new BasicHttpClientResponseHandler());
            logger.debug("Response -> {}", response);
        }
    }
}
