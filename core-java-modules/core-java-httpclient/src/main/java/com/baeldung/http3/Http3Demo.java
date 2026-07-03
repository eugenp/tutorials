package com.baeldung.http3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpOption;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Http3Demo {

    private final HttpClient client;

    public Http3Demo() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_3)
                .build();
    }

    public HttpResponse<String> fetch(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .setOption(HttpOption.H3_DISCOVERY, HttpOption.Http3DiscoveryMode.HTTP_3_URI_ONLY)
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    public static void main(String[] args) throws Exception {
        Http3Demo demo = new Http3Demo();
        HttpResponse<String> response = demo.fetch("https://cloudflare-quic.com/");

        System.out.println("Protocol: " + response.version());
        System.out.println("Status:   " + response.statusCode());
        System.out.println("Body:     " + response.body().length() + " bytes");
    }
}

