package com.baeldung.hexagonal.architecture.adapters;

import com.baeldung.hexagonal.architecture.ports.HttpPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientAdapter implements HttpPort {

    HttpClient client;

    public HttpClientAdapter(){
        client = HttpClient.newHttpClient();
    }


    public Object doGet(String url) throws IOException, InterruptedException {
        HttpRequest request = createRequest(url);
        HttpResponse response = getResponse(request);

        return response.body();
    }

    private HttpRequest createRequest(String url){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return request;
    }

    private HttpResponse getResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}
