package com.baeldung.proxyauth;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProxiedJavaHttpClient {

    private ProxiedJavaHttpClient() {
    }

    public static HttpClient createClient(ProxyConfig config) {
        ProxySelector proxySelector = ProxySelector.of(
            new InetSocketAddress(config.getHost(), config.getPort()));

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    config.getUsername(), 
                    config.getPassword().toCharArray());
            }
        };

        return HttpClient.newBuilder()
            .proxy(proxySelector)
            .authenticator(authenticator)
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
