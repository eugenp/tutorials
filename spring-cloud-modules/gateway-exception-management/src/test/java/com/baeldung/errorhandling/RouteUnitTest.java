package com.baeldung.errorhandling;

import com.github.tomakehurst.wiremock.http.Body;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"httpbin=http://localhost:${wiremock.server.port}/api"}
)
@AutoConfigureWireMock(port = 0)
class RouteUnitTest {

    @Value("${httpbin}")
    private String httpbin;

    @Value(value="${local.server.port}")
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void before() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port + "/test")
                .build();
    }

    @Test
    void whenRouteResponseStatusCodeIs404_thenApiGatewayShouldHandleResponse() {
        stubFor(post(urlEqualTo("/status/404"))
                .willReturn(
                    aResponse()
                        .withStatus(404)
                            .withResponseBody(Body.fromJsonBytes("{\"code\": 404, \"reason\": \"Not Found\"}".getBytes(StandardCharsets.UTF_8)))
                ));

        webTestClient.post()
                .uri("/error_404")
                .accept(MediaType.valueOf("application/json"))
                .contentType(MediaType.valueOf("application/json"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(404)
                .jsonPath("$.reason").isEqualTo("Not Found");
    }

    @Test
    void whenRouteResponseStatusCodeIs400_thenApiGatewayShouldHandleResponse() {
        stubFor(post(urlEqualTo("/status/400"))
                .willReturn(
                        aResponse()
                                .withStatus(400)
                                .withResponseBody(Body.fromJsonBytes("{\"code\": 400, \"reason\": \"Bad Request\"}".getBytes(StandardCharsets.UTF_8)))
                ));

        webTestClient.post()
                .uri("/error_400")
                .accept(MediaType.valueOf("application/json"))
                .contentType(MediaType.valueOf("application/json"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.code").isEqualTo(400)
                .jsonPath("$.reason").isEqualTo("Bad Request");
    }

    @Test
    void whenRouteResponseStatusCodeIs409_thenApiGatewayShouldHandleResponse() {
        stubFor(post(urlEqualTo("/status/409"))
                .willReturn(
                        aResponse()
                                .withStatus(409)
                                .withResponseBody(Body.fromJsonBytes("{\"code\": 409, \"reason\": \"Conflict\"}".getBytes(StandardCharsets.UTF_8)))
                ));

        webTestClient.post()
                .uri("/error_409")
                .accept(MediaType.valueOf("application/json"))
                .contentType(MediaType.valueOf("application/json"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.code").isEqualTo(409)
                .jsonPath("$.reason").isEqualTo("Conflict");
    }

    @Test
    void whenRouteResponseStatusCodeIs500_thenApiGatewayShouldHandleResponse() {
        stubFor(post(urlEqualTo("/status/500"))
                .willReturn(
                        aResponse()
                                .withStatus(500)
                                .withResponseBody(Body.fromJsonBytes("{\"code\": 500, \"reason\": \"Error\"}".getBytes(StandardCharsets.UTF_8)))
                ));

        webTestClient.post()
                .uri("/error_500")
                .accept(MediaType.valueOf("application/json"))
                .contentType(MediaType.valueOf("application/json"))
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.code").isEqualTo(500)
                .jsonPath("$.reason").isEqualTo("Error");
    }

    @Test
    void whenRouteAnythingRequest_thenApiGatewayShouldHandleResponse() {
        webTestClient.post()
                .uri("/anything")
                .contentType(MediaType.valueOf("application/json"))
                .body(BodyInserters.fromValue("{\"code\": 500, \"reason\": \"Error\"}".getBytes(StandardCharsets.UTF_8)))
                .accept(MediaType.valueOf("application/json"))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isEqualTo("{\"code\": 500, \"reason\": \"Error\"}");
    }

    @Test
    void whenRouteCustomRequestAuth_thenApiGatewayShouldHandleResponse() {
        webTestClient.post()
                .uri("/custom_auth")
                .contentType(MediaType.valueOf("application/json"))
                .body(BodyInserters.fromValue("{\"test\": \"test\", \"message\": \"test\"}".getBytes(StandardCharsets.UTF_8)))
                .accept(MediaType.valueOf("application/json"))
                .exchange()
                .expectStatus()
                .isUnauthorized()
                .expectBody()
                .jsonPath("$.path").isNotEmpty()
                .jsonPath("$.message").isEqualTo("Not authorized")
                .jsonPath("$.status").isEqualTo(401)
                .jsonPath("$.requestId").isNotEmpty()
                .jsonPath("$.timestamp").isNotEmpty();
    }

    @Test
    void whenRouteCustomRequestRateLimit_thenApiGatewayShouldHandleResponse() {
        webTestClient.post()
                .uri("/custom_rate_limit")
                .contentType(MediaType.valueOf("application/json"))
                .body(BodyInserters.fromValue("{\"test\": \"test\", \"message\": \"test\"}".getBytes(StandardCharsets.UTF_8)))
                .accept(MediaType.valueOf("application/json"))
                .exchange()
                .expectStatus()
                .isEqualTo(429)
                .expectBody()
                .jsonPath("$.path").isNotEmpty()
                .jsonPath("$.message").isEqualTo("Too many requests")
                .jsonPath("$.status").isEqualTo(429)
                .jsonPath("$.requestId").isNotEmpty()
                .jsonPath("$.timestamp").isNotEmpty();
    }

}
