package com.baeldung.httpclient.readresponsebodystring;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ApacheHttpClient5UnitTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    void whenUseApacheHttpClient_thenCorrect() throws IOException {
        HttpGet request = new HttpGet(DUMMY_URL);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String response = client.execute(request, new BasicHttpClientResponseHandler());
            logger.debug("Response -> {}", response);
        }
    }
}