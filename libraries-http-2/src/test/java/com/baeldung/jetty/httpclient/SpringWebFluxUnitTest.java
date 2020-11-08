package com.baeldung.jetty.httpclient;

import org.eclipse.jetty.client.HttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class SpringWebFluxUnitTest extends AbstractUnitTest {

    @Test
    public void givenReactiveClient_whenRequested_shouldReturnResponse() throws Exception {
        
        HttpClient httpClient = new HttpClient();
        httpClient.start();

        ClientHttpConnector clientConnector = new JettyClientHttpConnector(httpClient);
        WebClient client = WebClient.builder()
            .clientConnector(clientConnector)
            .build();
        String responseContent = client.post()
            .uri(uri())
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromPublisher(Mono.just(CONTENT), String.class))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        Assert.assertNotNull(responseContent);
        Assert.assertEquals(CONTENT, responseContent);
    }
}