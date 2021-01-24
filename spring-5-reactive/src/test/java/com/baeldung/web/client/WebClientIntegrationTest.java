package com.baeldung.web.client;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import com.baeldung.web.reactive.client.WebClientApplication;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@SpringBootTest(classes = WebClientApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebClientIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void demonstrateWebClient() {
        // request
        UriSpec<WebClient.RequestBodySpec> requestPost1 = createWebClientWithServerURLAndDefaultValues().method(HttpMethod.POST);
        UriSpec<WebClient.RequestBodySpec> requestPost2 = createWebClientWithServerURLAndDefaultValues().post();
        UriSpec<?> requestGet = createWebClientWithServerURLAndDefaultValues().get();

        // request body specifications
        RequestBodySpec bodySpecPost = requestPost1.uri("/resource");
        RequestBodySpec bodySpecPostMultipart = requestPost2.uri(uriBuilder -> uriBuilder.pathSegment("resource-multipart")
            .build());
        RequestBodySpec bodySpecOverridenBaseUri = requestPost2.uri(URI.create("/resource"));

        // request header specification
        String bodyValue = "bodyValue";
        RequestHeadersSpec<?> headerSpecPost1 = bodySpecPost.body(BodyInserters.fromPublisher(Mono.just(bodyValue), String.class));
        RequestHeadersSpec<?> headerSpecPost2 = bodySpecPost.body(BodyInserters.fromValue(bodyValue));
        RequestHeadersSpec<?> headerSpecGet = requestGet.uri("/resource");

        // request header specification using inserters
        BodyInserter<Publisher<String>, ReactiveHttpOutputMessage> inserterCompleteSuscriber = BodyInserters.fromPublisher(Subscriber::onComplete, String.class);

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key1", "multipartValue1");
        map.add("key2", "multipartValue2");

        BodyInserter<MultiValueMap<String, Object>, ClientHttpRequest> inserterMultipart = BodyInserters.fromMultipartData(map);
        BodyInserter<Object, ReactiveHttpOutputMessage> inserterObject = BodyInserters.fromValue(new Object());
        BodyInserter<String, ReactiveHttpOutputMessage> inserterString = BodyInserters.fromValue(bodyValue);

        RequestHeadersSpec<?> headerSpecInserterCompleteSuscriber = bodySpecPost.body(inserterCompleteSuscriber);
        RequestHeadersSpec<?> headerSpecInserterMultipart = bodySpecPostMultipart.body(inserterMultipart);
        RequestHeadersSpec<?> headerSpecInserterObject = bodySpecPost.body(inserterObject);
        RequestHeadersSpec<?> headerSpecInserterString = bodySpecPost.body(inserterString);

        // responses
        ResponseSpec responsePostObject = headerSpecInserterObject.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .acceptCharset(StandardCharsets.UTF_8)
            .ifNoneMatch("*")
            .ifModifiedSince(ZonedDateTime.now())
            .retrieve();
        String responsePostString = headerSpecInserterString.exchangeToMono(response -> response.bodyToMono(String.class))
            .block();
        String responsePostMultipart = headerSpecInserterMultipart.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        String responsePostCompleteSubsciber = headerSpecInserterCompleteSuscriber.retrieve()
            .bodyToMono(String.class)
            .block();
        String responsePostWithBody1 = headerSpecPost1.retrieve()
            .bodyToMono(String.class)
            .block();
        String responsePostWithBody3 = headerSpecPost2.retrieve()
            .bodyToMono(String.class)
            .block();
        String responseGet = headerSpecGet.retrieve()
            .bodyToMono(String.class)
            .block();
        String responsePostWithNoBody = bodySpecPost.retrieve()
            .bodyToMono(String.class)
            .block();
        try {
            bodySpecOverridenBaseUri.retrieve()
                .bodyToMono(String.class)
                .block();
        } catch (Exception ex) {
            System.out.println(ex.getClass());
        }
    }

    private WebClient createWebClient() {
        return WebClient.create();
    }

    private WebClient createWebClientWithServerURL() {
        return WebClient.create("http://localhost:8081");
    }

    private WebClient createWebClientConfiguringTimeout() {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    private WebClient createWebClientWithServerURLAndDefaultValues() {
        return WebClient.builder()
            .baseUrl("http://localhost:" + port)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
            .build();
    }
}
