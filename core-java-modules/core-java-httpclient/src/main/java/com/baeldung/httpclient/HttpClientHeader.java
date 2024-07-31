package com.baeldung.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientHeader {

    public static HttpResponse<String> callWithCustomHeader(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .header("X-Our-Header-1", "value1")
            .header("X-Our-Header-1", "value2")
            .header("X-Our-Header-2", "value2")
            .uri(new URI(url)).build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> callWithCustomHeaderV2(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .headers("X-Our-Header-1", "value1", "X-Our-Header-1", "value2")
            .uri(new URI(url)).build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> callWithCustomHeaderV3(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .setHeader("X-Our-Header-1", "value1")
            .setHeader("X-Our-Header-1", "value2")
            .uri(new URI(url)).build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }


}
