package com.baeldung.reactive.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.junit.jupiter.api.Test;
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
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WebClientApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class WebClientIntegrationTest {

    private static final String BODY_VALUE = "bodyValue";
    private static final ParameterizedTypeReference<Map<String, String>> MAP_RESPONSE_REF = new ParameterizedTypeReference<Map<String, String>>() {
    };

    @LocalServerPort
    private int port;

    @Test
    void givenDifferentWebClientCreationMethods_whenUsed_thenObtainExpectedResponse() {
        // WebClient creation
        WebClient client1 = WebClient.create();
        WebClient client2 = WebClient.create("http://localhost:" + port);
        WebClient client3 = WebClient.builder()
          .baseUrl("http://localhost:" + port)
          .defaultCookie("cookieKey", "cookieValue")
          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
          .build();

        // response assertions
        StepVerifier.create(retrieveResponse(client1.post()
            .uri("http://localhost:" + port + "/resource")))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(client2))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(client3))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        // assert response without specifying URI
        StepVerifier.create(retrieveResponse(client1))
          .expectErrorMatches(ex -> WebClientRequestException.class.isAssignableFrom(ex.getClass()) && ex.getMessage()
            .contains("Connection refused"))
          .verify();
    }

    @Test
    void givenDifferentMethodSpecifications_whenUsed_thenObtainExpectedResponse() {
        // request specification
        RequestBodyUriSpec uriSpecPost1 = createDefaultClient().method(HttpMethod.POST);
        RequestBodyUriSpec uriSpecPost2 = createDefaultClient().post();
        RequestHeadersUriSpec<?> requestGet = createDefaultClient().get();

        // response assertions
        StepVerifier.create(retrieveResponse(uriSpecPost1))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(uriSpecPost2))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveGetResponse(requestGet))
          .expectNextMatches(nextMap -> nextMap.get("field")
            .equals("value"))
          .verifyComplete();
    }

    @Test
    void givenDifferentUriSpecifications_whenUsed_thenObtainExpectedResponse() {
        // uri specification
        RequestBodySpec bodySpecUsingString = createDefaultPostRequest().uri("/resource");
        RequestBodySpec bodySpecUsingUriBuilder = createDefaultPostRequest().uri(
          uriBuilder -> uriBuilder.pathSegment("resource")
            .build());
        RequestBodySpec bodySpecusingURI = createDefaultPostRequest().uri(
          URI.create("http://localhost:" + port + "/resource"));
        RequestBodySpec bodySpecOverridenBaseUri = createDefaultPostRequest().uri(URI.create("/resource"));
        RequestBodySpec bodySpecOverridenBaseUri2 = WebClient.builder()
          .baseUrl("http://localhost:" + port)
          .build()
          .post()
          .uri(URI.create("/resource"));

        // response assertions
        StepVerifier.create(retrieveResponse(bodySpecUsingString))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(bodySpecUsingUriBuilder))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(bodySpecusingURI))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        // assert sending request overriding base URI
        StepVerifier.create(retrieveResponse(bodySpecOverridenBaseUri))
          .expectErrorMatches(ex -> WebClientRequestException.class.isAssignableFrom(ex.getClass()) && ex.getMessage()
            .contains("Connection refused"))
          .verify();
        StepVerifier.create(retrieveResponse(bodySpecOverridenBaseUri2))
          .expectErrorMatches(ex -> WebClientRequestException.class.isAssignableFrom(ex.getClass()) && ex.getMessage()
            .contains("Connection refused"))
          .verify();
    }

    @Test
    void givenDifferentBodySpecifications_whenUsed_thenObtainExpectedResponse() {
        // request body specifications
        RequestHeadersSpec<?> headersSpecPost1 = createDefaultPostResourceRequest().body(
          BodyInserters.fromPublisher(Mono.just(BODY_VALUE), String.class));
        RequestHeadersSpec<?> headersSpecPost2 = createDefaultPostResourceRequest().body(
          BodyInserters.fromValue(BODY_VALUE));
        RequestHeadersSpec<?> headersSpecPost3 = createDefaultPostResourceRequest().bodyValue(BODY_VALUE);
        RequestHeadersSpec<?> headersSpecFooPost = createDefaultPostRequest().uri("/resource-foo")
          .body(Mono.just(new Foo("fooName")), Foo.class);
        BodyInserter<Object, ReactiveHttpOutputMessage> inserterPlainObject = BodyInserters.fromValue(new Object());
        RequestHeadersSpec<?> headersSpecPlainObject = createDefaultPostResourceRequest().body(inserterPlainObject);

        // request body specifications - using other inserter method (multipart request)
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key1", "multipartValue1");
        map.add("key2", "multipartValue2");
        BodyInserter<MultiValueMap<String, Object>, ClientHttpRequest> inserterMultipart = BodyInserters.fromMultipartData(
          map);
        RequestHeadersSpec<?> headersSpecInserterMultipart = createDefaultPostRequest().uri("/resource-multipart")
          .body(inserterMultipart);

        // response assertions
        StepVerifier.create(retrieveResponse(headersSpecPost1))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(headersSpecPost2))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(headersSpecPost3))
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(headersSpecFooPost))
          .expectNext("processedFoo-fooName")
          .verifyComplete();
        StepVerifier.create(retrieveResponse(headersSpecInserterMultipart))
          .expectNext("processed-multipartValue1-multipartValue2")
          .verifyComplete();
        // assert error plain `new Object()` as request body
        StepVerifier.create(retrieveResponse(headersSpecPlainObject))
          .expectError(CodecException.class)
          .verify();
        // assert response for request with no body
        Mono<Map<String, String>> responsePostWithNoBody = createDefaultPostResourceRequest().exchangeToMono(
          responseHandler -> {
              assertThat(responseHandler.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
              return responseHandler.bodyToMono(MAP_RESPONSE_REF);
          });
        StepVerifier.create(responsePostWithNoBody)
          .expectNextMatches(nextMap -> nextMap.get("error")
            .equals("Bad Request"))
          .verifyComplete();
    }

    @Test
    void givenPostSpecifications_whenHeadersAdded_thenObtainExpectedResponse() {
        // request header specification
        RequestHeadersSpec<?> headersSpecInserterStringWithHeaders = createDefaultPostResourceRequestResponse().header(
            HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
          .acceptCharset(StandardCharsets.UTF_8)
          .ifNoneMatch("*")
          .ifModifiedSince(ZonedDateTime.now());

        // response assertions
        StepVerifier.create(retrieveResponse(headersSpecInserterStringWithHeaders))
          .expectNext("processed-bodyValue")
          .verifyComplete();
    }

    @Test
    void givenDifferentResponseSpecifications_whenUsed_thenObtainExpectedResponse() {
        ResponseSpec responseSpecPostString = createDefaultPostResourceRequestResponse().retrieve();
        Mono<String> responsePostString = responseSpecPostString.bodyToMono(String.class);
        Mono<String> responsePostString2 = createDefaultPostResourceRequestResponse().exchangeToMono(response -> {
            if (response.statusCode() == HttpStatus.OK) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException()
                  .flatMap(Mono::error);
            }
        });
        Mono<String> responsePostNoBody = createDefaultPostResourceRequest().exchangeToMono(response -> {
            if (response.statusCode() == HttpStatus.OK) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException()
                  .flatMap(Mono::error);
            }
        });
        Mono<Map<String, String>> responseGet = createDefaultClient().get()
          .uri("/resource")
          .retrieve()
          .bodyToMono(MAP_RESPONSE_REF);

        // response assertions
        StepVerifier.create(responsePostString)
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(responsePostString2)
          .expectNext("processed-bodyValue")
          .verifyComplete();
        StepVerifier.create(responsePostNoBody)
          .expectNext("Error response")
          .verifyComplete();
        StepVerifier.create(responseGet)
          .expectNextMatches(nextMap -> nextMap.get("field")
            .equals("value"))
          .verifyComplete();
    }

    @Test
    void givenWebClientWithTimeoutConfigurations_whenRequestUsingWronglyConfiguredPublisher_thenObtainTimeout() {
        HttpClient httpClient = HttpClient.create()
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
          .responseTimeout(Duration.ofMillis(1000))
          .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS))
            .addHandlerLast(new WriteTimeoutHandler(1000, TimeUnit.MILLISECONDS)));

        WebClient timeoutClient = WebClient.builder()
          .baseUrl("http://localhost:" + port)
          .clientConnector(new ReactorClientHttpConnector(httpClient))
          .build();

        RequestHeadersSpec<?> neverendingMonoBodyRequest = timeoutClient.post()
          .uri("/resource")
          .body(Mono.never(), String.class);

        StepVerifier.create(neverendingMonoBodyRequest.retrieve()
            .bodyToMono(String.class))
          .expectErrorMatches(ex -> WebClientRequestException.class.isAssignableFrom(ex.getClass())
            && ReadTimeoutException.class.isAssignableFrom(ex.getCause().getClass()))
          .verify();
    }

    // helper methods to create default instances
    private WebClient createDefaultClient() {
        return WebClient.create("http://localhost:" + port);
    }

    private RequestBodyUriSpec createDefaultPostRequest() {
        return createDefaultClient().post();
    }

    private RequestBodySpec createDefaultPostResourceRequest() {
        return createDefaultPostRequest().uri("/resource");
    }

    private RequestHeadersSpec<?> createDefaultPostResourceRequestResponse() {
        return createDefaultPostResourceRequest().bodyValue(BODY_VALUE);
    }

    // helper methods to retrieve a response based on different steps of the process (specs)
    private Mono<String> retrieveResponse(WebClient client) {
        return client.post()
          .uri("/resource")
          .bodyValue(BODY_VALUE)
          .retrieve()
          .bodyToMono(String.class);
    }

    private Mono<String> retrieveResponse(RequestBodyUriSpec spec) {
        return spec.uri("/resource")
          .bodyValue(BODY_VALUE)
          .retrieve()
          .bodyToMono(String.class);
    }

    private Mono<Map<String, String>> retrieveGetResponse(RequestHeadersUriSpec<?> spec) {
        return spec.uri("/resource")
          .retrieve()
          .bodyToMono(MAP_RESPONSE_REF);
    }

    private Mono<String> retrieveResponse(RequestBodySpec spec) {
        return spec.bodyValue(BODY_VALUE)
          .retrieve()
          .bodyToMono(String.class);
    }

    private Mono<String> retrieveResponse(RequestHeadersSpec<?> spec) {
        return spec.retrieve()
          .bodyToMono(String.class);
    }
}
