package com.baeldung.proxyauth;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

public class ProxiedWebClient {

    private ProxiedWebClient() {
    }

    public static WebClient createClient(ProxyConfig config) {
        HttpClient httpClient = createHttpClient(config);

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    private static HttpClient createHttpClient(ProxyConfig config) {
        if (config.requiresAuth()) {
            return HttpClient.create()
                .proxy(proxy -> proxy
                    .type(ProxyProvider.Proxy.HTTP)
                    .host(config.getHost())
                    .port(config.getPort())
                    .username(config.getUsername())
                    .password(u -> config.getPassword()));
        } else {
            return HttpClient.create()
                .proxy(proxy -> proxy
                    .type(ProxyProvider.Proxy.HTTP)
                    .host(config.getHost())
                    .port(config.getPort()));
        }
    }

    public static String sendRequest(WebClient webClient, String url) {
        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
