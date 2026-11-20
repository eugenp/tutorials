package com.baeldung.proxyauth;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProxiedJavaHttpClient {

    private ProxiedJavaHttpClient() {
    }

    public static HttpClient createClient(ProxyConfig config) {
        return HttpClient.newBuilder()
            .proxy(config.proxySelector())
            .authenticator(config.authenticator())
            .build();
    }

    public static String sendRequest(HttpClient client, String url) 
      throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = client.send(
            request, 
            HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
