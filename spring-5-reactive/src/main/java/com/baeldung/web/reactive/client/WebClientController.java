package com.baeldung.web.reactive.client;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Collections;

@RestController
public class WebClientController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/resource")
    public void getResource() {
    }

    public void demonstrateWebClient() {
        // request
        WebClient.UriSpec<WebClient.RequestBodySpec> request1 = createWebClientWithServerURLAndDefaultValues().method(HttpMethod.POST);
        WebClient.UriSpec<WebClient.RequestBodySpec> request2 = createWebClientWithServerURLAndDefaultValues().post();

        // request body specifications
        WebClient.RequestBodySpec uri1 = createWebClientWithServerURLAndDefaultValues().method(HttpMethod.POST)
            .uri("/resource");
        WebClient.RequestBodySpec uri2 = createWebClientWithServerURLAndDefaultValues().post()
            .uri(URI.create("/resource"));

        // request header specification
        WebClient.RequestHeadersSpec<?> requestSpec1 = uri1.body(BodyInserters.fromPublisher(Mono.just("data"), String.class));
        WebClient.RequestHeadersSpec<?> requestSpec2 = uri2.body(BodyInserters.fromObject("data"));

        // inserters
        BodyInserter<Publisher<String>, ReactiveHttpOutputMessage> inserter1 = BodyInserters
                .fromPublisher(Subscriber::onComplete, String.class);

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key1", "value1");
        map.add("key2", "value2");

        // BodyInserter<MultiValueMap<String, ?>, ClientHttpRequest> inserter2 = BodyInserters.fromMultipartData(map);
        BodyInserter<String, ReactiveHttpOutputMessage> inserter3 = BodyInserters.fromObject("body");

        // responses
        WebClient.ResponseSpec response1 = uri1.body(inserter3)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .acceptCharset(Charset.forName("UTF-8"))
            .ifNoneMatch("*")
            .ifModifiedSince(ZonedDateTime.now())
            .retrieve();
        WebClient.ResponseSpec response2 = requestSpec2.retrieve();

    }

    private WebClient createWebClient() {
        return WebClient.create();
    }

    private WebClient createWebClientWithServerURL() {
        return WebClient.create("http://localhost:8081");
    }

    private WebClient createWebClientWithServerURLAndDefaultValues() {
        return WebClient.builder()
            .baseUrl("http://localhost:8081")
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
            .build();
    }

}
