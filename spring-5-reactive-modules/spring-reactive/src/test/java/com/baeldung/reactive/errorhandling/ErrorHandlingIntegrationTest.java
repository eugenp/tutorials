package com.baeldung.reactive.errorhandling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWebTestClient(timeout = "10000")
class ErrorHandlingIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void givenErrorReturn_whenUsernamePresent_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint1?name={username}", "Tony")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hello, Tony");
    }

    @Test
    void givenErrorReturn_whenNoUsername_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint1")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hello, Stranger");
    }

    @Test
    void givenResumeFallback_whenUsernamePresent_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint2?name={username}", "Tony")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hello, Tony");
    }

    @Test
    void givenResumeFallback_whenNoUsername_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint2")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hello, Stranger");
    }

    @Test
    void givenResumeDynamicValue_whenUsernamePresent_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint3?name={username}", "Tony")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hello, Tony");
    }

    @Test
    void givenResumeDynamicValue_whenNoUsername_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint3")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hi, I looked around for your name but found: No value present");
    }

    @Test
    void givenResumeRethrow_whenUsernamePresent_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint4?name={username}", "Tony")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hello, Tony");
    }

    @Test
    void givenResumeRethrow_whenNoUsername_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint4")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectStatus().isBadRequest()
          .expectHeader().contentType(MediaType.APPLICATION_JSON)
          .expectBody().jsonPath("$.message").isEqualTo("please provide a name");
    }

    @Test
    void givenGlobalErrorHandling_whenUsernamePresent_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint5?name={username}", "Tony")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectBody(String.class).isEqualTo("Hello, Tony");
    }

    @Test
    void givenGlobalErrorHandling_whenNoUsername_thenOk() {

        webTestClient.get()
          .uri("/api/endpoint5")
          .accept(MediaType.TEXT_PLAIN)
          .exchange()
          .expectStatus().isBadRequest()
          .expectHeader().contentType(MediaType.APPLICATION_JSON)
          .expectBody().jsonPath("$.message").isEqualTo("please provide a name");
    }

}
