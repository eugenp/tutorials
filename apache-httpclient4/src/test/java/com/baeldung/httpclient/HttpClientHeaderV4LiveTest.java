package com.baeldung.httpclient;

import static org.apache.http.HttpHeaders.USER_AGENT;

import java.io.IOException;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HttpClientHeaderV4LiveTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SAMPLE_URL = "http://www.github.com";

    @Test
    void givenRequestBuildWithBuilder_whenRequestHasCustomContentType_thenCorrect() throws IOException {
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
            .setUri(SAMPLE_URL)
            .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();
        client.execute(request);
    }

    @Test
    void whenClientUsesCustomUserAgent_thenCorrect() throws IOException {
        CloseableHttpClient client = HttpClients.custom()
            .setUserAgent("Mozilla/5.0 Firefox/26.0")
            .build();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        String response = client.execute(request, new BasicResponseHandler());
        logger.info("Response -> {}", response);
    }

    @Test
    void whenRequestHasCustomUserAgent_thenCorrect() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        request.setHeader(USER_AGENT,"Mozilla/5.0 Firefox/26.0");
        String response = client.execute(request, new BasicResponseHandler());
        logger.info("Response -> {}", response);
    }
}