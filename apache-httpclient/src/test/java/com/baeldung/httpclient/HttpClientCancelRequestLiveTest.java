package com.baeldung.httpclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

class HttpClientCancelRequestLiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    @Test
    void whenRequestIsCanceled_thenCorrect() throws IOException {
        HttpGet request = new HttpGet(SAMPLE_URL);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            httpClient.execute(request, response -> {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getCode());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                System.out.println("----------------------------------------");

                if (entity != null) {
                    // Closes this stream and releases any system resources
                    entity.close();
                }

                // Do not feel like reading the response body
                // Call abort on the request object
                request.abort();

                assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
                return response;
            });
        }
    }
}
