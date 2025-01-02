package com.baeldung.javahttpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import com.baeldung.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class JavaHttpClientPostService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public JavaHttpClientPostService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Post> getPosts() {
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("http://localhost:8080/posts"))
          .GET()
          .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<ArrayList<Post>>() {
            });
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch posts", e);
        }
    }

}
