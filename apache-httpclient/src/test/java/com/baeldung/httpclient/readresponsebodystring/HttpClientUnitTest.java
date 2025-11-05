package com.baeldung.httpclient.readresponsebodystring;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class HttpClientUnitTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    void whenUseHttpClient_thenCorrect() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DUMMY_URL)).build();

        // synchronous response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.debug(response.body());

        // asynchronous response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(logger::debug)
            .join();
    }
}
