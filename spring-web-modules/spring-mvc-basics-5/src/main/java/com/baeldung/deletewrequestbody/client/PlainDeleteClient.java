package com.baeldung.deletewrequestbody.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;

public class PlainDeleteClient {

    private final String url;
    private HttpClient client = HttpClient.newHttpClient();

    public PlainDeleteClient(String url) {
        this.url = url;
    }

    public String delete(String json) throws InterruptedException, IOException {
        BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
            .header("Content-Type", "application/json")
            .method("DELETE", body)
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
