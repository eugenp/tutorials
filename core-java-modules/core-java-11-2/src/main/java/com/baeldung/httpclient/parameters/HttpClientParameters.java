package com.baeldung.httpclient.parameters;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientParameters {

    public static void main(String[] args) throws Exception {
        queryParametersRequest();
        queryParametersRequestWithDefaultConfiguration();
    }

    public static void queryParametersRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/get?param1=value1&param2=value2"))
          .GET()
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        printResponse(response);
    }

    public static void queryParametersRequestWithDefaultConfiguration() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://postman-echo.com/get?param1=value1&param2=value2"))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        printResponse(response);
    }

    private static void printResponse(HttpResponse<String> response) {
        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }
}