package com.baeldung.jetty.httpclient;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class SpringReactorUnitTest extends AbstractTest {

    @Test
    public void testResponseWithContent() throws Exception {

        WebClient client = WebClient.builder()
            .clientConnector(new JettyClientHttpConnector(httpClient))
            .build();
        String responseContent = client.post()
            .uri(uri()).contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromPublisher(Mono.just(content), String.class))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        Assert.assertNotNull(responseContent);
        Assert.assertEquals(content, responseContent);
    }
}
