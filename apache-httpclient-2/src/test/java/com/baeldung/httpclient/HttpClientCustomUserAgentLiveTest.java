package com.baeldung.httpclient;

import org.apache.hc.client5.http.classic.methods.HttpGet;

import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.apache.hc.core5.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpClientCustomUserAgentLiveTest extends GetRequestMockServer {
    private static Logger log = LoggerFactory.getLogger(HttpClientCustomUserAgentLiveTest.class);

    @Test
    void whenClientUsesCustomUserAgent_thenCorrect() throws IOException {
        CloseableHttpClient client = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 Firefox/26.0")
                .build();
        final HttpGet request = new HttpGet(serviceOneUrl);
        String response = client.execute(request, new BasicHttpClientResponseHandler());
        assertTrue(response.equals("{\"status\":\"ok\"}"));
    }

    @Test
    void whenRequestHasCustomUserAgent_thenCorrect() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet request = new HttpGet(serviceTwoUrl);
        request.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Firefox/26.0");
        String response = client.execute(request, new BasicHttpClientResponseHandler());
        assertTrue(response.equals("{\"status\":\"ok\"}"));
    }

}
