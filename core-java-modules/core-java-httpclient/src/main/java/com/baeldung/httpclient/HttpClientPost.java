package com.baeldung.httpclient;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class HttpClientPost {

    public static HttpResponse<String> sendSynchronousPost(String serviceUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();

        HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static CompletableFuture<HttpResponse<String>> sendAsynchronousPost(String serviceUrl) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client
          .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        return futureResponse;
    }

    public static List<CompletableFuture<HttpResponse<String>>> sendConcurrentPost(List<String> serviceUrls) {
        HttpClient client = HttpClient.newHttpClient();

        List<CompletableFuture<HttpResponse<String>>> completableFutures = serviceUrls.stream()
          .map(URI::create)
          .map(HttpRequest::newBuilder)
          .map(builder -> builder.POST(HttpRequest.BodyPublishers.noBody()))
          .map(HttpRequest.Builder::build)
          .map(request -> client.sendAsync(request, HttpResponse.BodyHandlers.ofString()))
          .collect(Collectors.toList());

        return completableFutures;
    }

    public static HttpResponse<String> sendPostWithAuthHeader(String serviceUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.noBody())
          .header("Authorization", "Basic " + Base64.getEncoder()
            .encodeToString(("baeldung:123456").getBytes()))
          .build();

        HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static HttpResponse<String> sendPostWithAuthClient(String serviceUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
          .authenticator(new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(
                    "baeldung",
                    "123456".toCharArray());
              }
          })
          .build();

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();

        HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static HttpResponse<String> sendPostWithJsonBody(String serviceUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.ofString("{\"action\":\"hello\"}"))
          .build();

        HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static HttpResponse<String> sendPostWithFormData(String serviceUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Map<String, String> formData = new HashMap<>();
        formData.put("username", "baeldung");
        formData.put("message", "hello");

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
          .build();

        HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static HttpResponse<String> sendPostWithFileData(String serviceUrl, Path file) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(serviceUrl))
          .POST(HttpRequest.BodyPublishers.ofFile(file))
          .build();

        HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }

}
