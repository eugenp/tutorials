package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

class HttpClientRedirectV4LiveTest {
    private CloseableHttpClient instance;

    private CloseableHttpResponse response;

    @BeforeEach
    public final void before() {
        instance = HttpClientBuilder.create()
            .build();
    }

    @AfterEach
    public final void after() throws IllegalStateException, IOException {
        ResponseUtil.closeResponse(response);
    }

    @Test
    void givenRedirectsAreDisabled_whenConsumingUrlWhichRedirects_thenNotRedirected() throws IOException {
        instance = HttpClients.custom()
            .disableRedirectHandling()
            .build();

        final HttpGet httpGet = new HttpGet("http://t.co/I5YYd9tddw");
        response = instance.execute(httpGet);

        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(301));
    }

    // redirect with POST

    @Test
    void givenPostRequest_whenConsumingUrlWhichRedirects_thenNotRedirected() throws IOException {
        instance = HttpClientBuilder.create()
            .build();
        response = instance.execute(new HttpPost("http://t.co/I5YYd9tddw"));
        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(301));
    }

    @Test
    void givenRedirectingPOST_whenConsumingUrlWhichRedirectsWithPOST_thenRedirected() throws IOException {
        instance = HttpClientBuilder.create()
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build();
        response = instance.execute(new HttpPost("http://t.co/I5YYd9tddw"));
        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
    }

}