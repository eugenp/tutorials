package com.baeldung.httpclient.parameters;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientParametersLiveTest {

    private static HttpClient client;

    @BeforeAll
    public static void setUp() {
        client = HttpClient.newHttpClient();
    }

    @Test
    public void givenQueryParams_whenGetRequest_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/get?param1=value1&param2=value2"))
          .GET()
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenQueryParams_whenGetRequestWithDefaultConfiguration_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://postman-echo.com/get?param1=value1&param2=value2"))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(response.statusCode(), 200);
    }

}
