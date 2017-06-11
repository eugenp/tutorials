package com.baeldung.web.reactive.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class ReactiveWebClientService {

    @Value("${server.port}")
    private String port;

    public void performRequestToTestEndpoint() {

        WebClient client = WebClient
                .builder()
                    .baseUrl("http://localhost:" + port)
                    .defaultCookie("ReadArticlesNumber", "100")
                    .defaultHeader("Authentication", "TokenValue")
                    .defaultUriVariables(Collections.singletonMap("url", "http://localhost:" + port))
                .build();

        String response = client
                    .post()
                    .uri("/test")
                    .body(BodyInserters.fromObject("test"))
                    .retrieve()
                    .bodyToMono(String.class)
                .block();

    }

}
