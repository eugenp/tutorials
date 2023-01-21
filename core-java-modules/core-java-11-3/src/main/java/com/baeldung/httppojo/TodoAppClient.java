package com.baeldung.httppojo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class TodoAppClient {

    public Todo syncGson() throws Exception {
        String response = sampleApiRequest();

        Gson gson = new GsonBuilder().create();

        List<Todo> todo = gson.fromJson(response, new TypeToken<List<Todo>>() {
        }.getType());

        return todo.get(1);

    }

    public Todo syncJackson() throws Exception {
        String response = sampleApiRequest();

        ObjectMapper objectMapper = new ObjectMapper();
        Todo[] todo = objectMapper.readValue(response, Todo[].class);

        return todo[1];

    }

    public String sampleApiRequest() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();

    }

    public Todo asyncJackson() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();

        ObjectMappingJackson o = new ObjectMappingJackson();

        List<Todo> todo = HttpClient.newHttpClient()
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(o::readValue)
            .get();

        return todo.get(1);

    }

    public Todo asyncGson() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();
        ObjectMappingGson o = new ObjectMappingGson();

        List<Todo> todo = HttpClient.newHttpClient()
            .sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(o::readValue)
            .get();

        return todo.get(1);

    }

}