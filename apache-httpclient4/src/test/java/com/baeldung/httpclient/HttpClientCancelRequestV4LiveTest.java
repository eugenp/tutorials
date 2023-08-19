package com.baeldung.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpClientCancelRequestV4LiveTest {

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

    @Test
    public final void whenRequestIsCanceled_thenCorrect() throws IOException {
        instance = HttpClients.custom().build();
        final HttpGet request = new HttpGet(SAMPLE_URL);
        response = instance.execute(request);

        try {
            final HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                entity.getContent().close();
            }

            System.out.println("----------------------------------------");

            // Do not feel like reading the response body
            // Call abort on the request object
            request.abort();
        } finally {
            response.close();
        }
    }
}
