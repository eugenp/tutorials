/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.httpclient;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpRequest.BodyProcessor;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

/**
 *
 * @author pkaria
 */
public class HttpClientExample {
    
    public static void main(String[] args) throws Exception {
        httpGetRequest();
        httpPostRequest();
        asynchronousRequest();
        asynchronousMultipleRequests();
    }

    public static void httpGetRequest() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI httpURI = new URI("http://jsonplaceholder.typicode.com/posts/1");
        HttpRequest request = HttpRequest.newBuilder(httpURI).GET()
          .headers("Accept-Enconding", "gzip, deflate").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
        String responseBody = response.body();
        int responseStatusCode = response.statusCode();
        System.out.println(responseBody);
    }

    public static void httpPostRequest() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient
                .newBuilder()
                .build();
        HttpRequest request = HttpRequest
                .newBuilder(new URI("http://jsonplaceholder.typicode.com/posts"))
                .POST(BodyProcessor.fromString("Sample Post Request"))
                .build();
        HttpResponse<String> response
                = client.send(request, HttpResponse.BodyHandler.asString());
        String responseBody = response.body();
        System.out.println(responseBody);
    }

    public static void asynchronousRequest() throws URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        URI httpURI = new URI("http://jsonplaceholder.typicode.com/posts/1");
        HttpRequest request = HttpRequest.newBuilder(httpURI).GET().build();
        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request,
                HttpResponse.BodyHandler.asString());
    }

    public static void asynchronousMultipleRequests() throws URISyntaxException {
        List<URI> targets = Arrays.asList(new URI("http://jsonplaceholder.typicode.com/posts/1"), new URI("http://jsonplaceholder.typicode.com/posts/2"));
        HttpClient client = HttpClient.newHttpClient();
        List<CompletableFuture<File>> futures = targets
                .stream()
                .map(target -> client
                .sendAsync(
                        HttpRequest.newBuilder(target)
                                .GET()
                                .build(),
                        BodyHandler.asFile(Paths.get("base", target.getPath())))
                .thenApply(response -> response.body())
                .thenApply(path -> path.toFile()))
                .collect(Collectors.toList());
    }
}
