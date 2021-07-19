package com.baeldung.jetty.httpclient;

import org.eclipse.jetty.client.HttpClient;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class SpringWebFluxUnitTest extends AbstractUnitTest {
    
    protected static int port = 9083;
    
    @BeforeAll
    public static void init() {
        startServer(new RequestHandler(), port);
        startClient();
    }
    
    @Test
    public void givenReactiveClient_whenRequested_shouldReturnResponse() throws Exception {

        ClientHttpConnector clientConnector = new JettyClientHttpConnector(httpClient);
        WebClient client = WebClient.builder()
            .clientConnector(clientConnector)
            .build();
        String responseContent = client.post()
            .uri(uri(port))
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromPublisher(Mono.just(CONTENT), String.class))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        assertNotNull(responseContent);
        assertEquals(CONTENT, responseContent);
    }
    
    @AfterAll
    public static void dispose() throws Exception {
        if (httpClient != null) {
            httpClient.stop();
        }
        if (server != null) {
            server.stop();
        }
    }
}