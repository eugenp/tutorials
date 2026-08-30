package com.baeldung.http3client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Http3ClientUnitTest {

    @Test
    void givenHttp3Client_whenSendingARequest_thenShouldReceiveSuccessfulResponse()
        throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_3)
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://example.com"))
            .GET()
            .build();

        HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }
}