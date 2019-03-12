package com.baeldung.serverconfig.client;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.SneakyThrows;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class GreetingWebClient {

    private WebClient client = getWebClient();

    public void getResult() {
        System.out.println("Mono");
        Mono<String> greetingMono = client.get()
                                          .uri("/greet/{name}", "baeldung")
                                          .retrieve()
                                          .bodyToMono(String.class);

        greetingMono.subscribe(System.out::println);
    }

    @SneakyThrows
    private WebClient getWebClient() {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        return WebClient.builder()
                        .baseUrl("https://localhost:8443")
                        .clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }
}