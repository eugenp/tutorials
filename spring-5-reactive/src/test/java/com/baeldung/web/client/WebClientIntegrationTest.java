package com.baeldung.web.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.codec.CodecException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.baeldung.web.reactive.client.Foo;
import com.baeldung.web.reactive.client.WebClientApplication;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

@SpringBootTest(classes = WebClientApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebClientIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void givenDifferentScenarios_whenRequestsSent_thenObtainExpectedResponses() {
        // WebClient
        WebClient client1 = WebClient.create();
        WebClient client2 = WebClient.create("http://localhost:" + port);
        WebClient client3 = WebClient.builder()
            .baseUrl("http://localhost:" + port)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
            .build();

        // request specification
        UriSpec<RequestBodySpec> uriSpecPost1 = client1.method(HttpMethod.POST);
        UriSpec<RequestBodySpec> uriSpecPost2 = client2.post();
        UriSpec<?> requestGet = client3.get();

        // uri specification
        RequestBodySpec bodySpecPost = uriSpecPost1.uri("http://localhost:" + port + "/resource");
        RequestBodySpec bodySpecPostMultipart = uriSpecPost2.uri(uriBuilder -> uriBuilder.pathSegment("resource-multipart")
            .build());
        RequestBodySpec fooBodySpecPost = createDefaultPostRequest().uri("/resource-foo");
        RequestBodySpec bodySpecOverridenBaseUri = client3.post()
            .uri(URI.create("/resource"));

        // request body specifications
        String bodyValue = "bodyValue";
        RequestHeadersSpec<?> headersSpecPost1 = bodySpecPost.body(BodyInserters.fromPublisher(Mono.just(bodyValue), String.class));
        RequestHeadersSpec<?> headersSpecPost2 = createDefaultPostResourceRequest().body(BodyInserters.fromValue(bodyValue));
        RequestHeadersSpec<?> headersSpecPost3 = createDefaultPostResourceRequest().bodyValue(bodyValue);
        RequestHeadersSpec<?> headersSpecFooPost = fooBodySpecPost.body(Mono.just(new Foo("fooName")), Foo.class);
        RequestHeadersSpec<?> headersSpecGet = requestGet.uri("/resource");

        // request body specifications - using inserters
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key1", "multipartValue1");
        map.add("key2", "multipartValue2");

        BodyInserter<MultiValueMap<String, Object>, ClientHttpRequest> inserterMultipart = BodyInserters.fromMultipartData(map);
        BodyInserter<Object, ReactiveHttpOutputMessage> inserterObject = BodyInserters.fromValue(new Object());
        BodyInserter<String, ReactiveHttpOutputMessage> inserterString = BodyInserters.fromValue(bodyValue);

        RequestHeadersSpec<?> headersSpecInserterMultipart = bodySpecPostMultipart.body(inserterMultipart);
        RequestHeadersSpec<?> headersSpecInserterObject = createDefaultPostResourceRequest().body(inserterObject);
        RequestHeadersSpec<?> headersSpecInserterString = createDefaultPostResourceRequest().body(inserterString);

        // request header specification
        RequestHeadersSpec<?> headersSpecInserterStringWithHeaders = headersSpecInserterString.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .acceptCharset(StandardCharsets.UTF_8)
            .ifNoneMatch("*")
            .ifModifiedSince(ZonedDateTime.now());

        // request
        ResponseSpec responseSpecPostString = headersSpecInserterStringWithHeaders.retrieve();
        Mono<String> responsePostString = responseSpecPostString.bodyToMono(String.class);
        Mono<String> responsePostMultipart = headersSpecInserterMultipart.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
            .retrieve()
            .bodyToMono(String.class);
        Mono<String> responsePostWithBody1 = headersSpecPost1.retrieve()
            .bodyToMono(String.class);
        Mono<String> responsePostWithBody2 = headersSpecPost2.retrieve()
            .bodyToMono(String.class);
        Mono<String> responsePostWithBody3 = headersSpecPost3.exchangeToMono(response -> {
            if (response.statusCode()
                .equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode()
                .is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException()
                    .flatMap(Mono::error);
            }
        });
        Mono<String> responsePostFoo = headersSpecFooPost.retrieve()
            .bodyToMono(String.class);
        ParameterizedTypeReference<Map<String, String>> ref = new ParameterizedTypeReference<Map<String, String>>() {
        };
        Mono<Map<String, String>> responseGet = headersSpecGet.retrieve()
            .bodyToMono(ref);
        Mono<Map<String, String>> responsePostWithNoBody = createDefaultPostResourceRequest().exchangeToMono(responseHandler -> {
            assertThat(responseHandler.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            return responseHandler.bodyToMono(ref);
        });
        Mono<String> responsePostOverridenBaseUri = bodySpecOverridenBaseUri.retrieve()
            .bodyToMono(String.class);

        // response assertions
        StepVerifier.create(responsePostString)
            .expectNext("processed-bodyValue")
            .verifyComplete();
        StepVerifier.create(responsePostMultipart)
            .expectNext("processed-multipartValue1-multipartValue2")
            .verifyComplete();
        StepVerifier.create(responsePostWithBody1)
            .expectNext("processed-bodyValue")
            .verifyComplete();
        StepVerifier.create(responsePostWithBody2)
            .expectNext("processed-bodyValue")
            .verifyComplete();
        StepVerifier.create(responsePostWithBody3)
            .expectNext("processed-bodyValue")
            .verifyComplete();
        StepVerifier.create(responseGet)
            .expectNextMatches(nextMap -> nextMap.get("field")
                .equals("value"))
            .verifyComplete();
        StepVerifier.create(responsePostFoo)
            .expectNext("processedFoo-fooName")
            .verifyComplete();
        StepVerifier.create(responsePostWithNoBody)
            .expectNextMatches(nextMap -> nextMap.get("error")
                .equals("Bad Request"))
            .verifyComplete();
        // assert sending request overriding base uri
        StepVerifier.create(responsePostOverridenBaseUri)
            .expectErrorMatches(ex -> WebClientRequestException.class.isAssignableFrom(ex.getClass()) && ex.getMessage()
                .contains("Connection refused"))
            .verify();
        // assert error plain `new Object()` as request body
        StepVerifier.create(headersSpecInserterObject.exchangeToMono(response -> response.bodyToMono(String.class)))
            .expectError(CodecException.class)
            .verify();
    }

    @Test
    public void givenWebClientWithTimeoutConfigurations_whenRequestUsingWronglyConfiguredPublisher_thenObtainTimeout() {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
            .responseTimeout(Duration.ofMillis(1000))
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(1000, TimeUnit.MILLISECONDS)));

        WebClient timeoutClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

        BodyInserter<Publisher<String>, ReactiveHttpOutputMessage> inserterCompleteSuscriber = BodyInserters.fromPublisher(Subscriber::onComplete, String.class);
        RequestHeadersSpec<?> headersSpecInserterCompleteSuscriber = timeoutClient.post()
            .uri("/resource")
            .body(inserterCompleteSuscriber);

        StepVerifier.create(headersSpecInserterCompleteSuscriber.retrieve()
            .bodyToMono(String.class))
            .expectTimeout(Duration.ofMillis(2000))
            .verify();
    }

    private RequestBodyUriSpec createDefaultPostRequest() {
        return WebClient.create("http://localhost:" + port)
            .post();
    }

    private RequestBodySpec createDefaultPostResourceRequest() {
        return createDefaultPostRequest().uri("/resource");
    }
}
