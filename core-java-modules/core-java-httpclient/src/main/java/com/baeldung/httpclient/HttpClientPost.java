package com.baeldung.httpclient;

import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class HttpClientPost {

    public static HttpResponse<String> sendSynchronousPost(String serviceUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public static CompletableFuture<HttpResponse<String>> sendAsynchronousPost(String serviceUrl) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();
        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return futureResponse;
    }

    public static CompletableFuture<HttpResponse<String>> sendConcurrentPost(List<String> serviceUrls) {
        HttpClient client = HttpClient.newHttpClient();

        List<HttpRequest> requests = serviceUrls.stream()
          .map(URI::create)
          .map(HttpRequest::newBuilder)
          .map(HttpRequest.Builder::build)
          .collect(Collectors.toList());

        CompletableFuture.allOf(requests.stream()
            .map(request -> client.sendAsync(request, HttpResponse.BodyHandlers.ofString()))
            .toArray(CompletableFuture<?>[]::new))
          .join();

        throw new NotImplementedException();
    }

}
