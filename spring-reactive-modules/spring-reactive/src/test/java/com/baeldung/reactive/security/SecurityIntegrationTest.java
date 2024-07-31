package com.baeldung.reactive.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = SpringSecurity6Application.class)
class SecurityIntegrationTest {

    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToApplicationContext(context)
          .configureClient()
          .build();
    }

    @Test
    void whenNoCredentials_thenRedirectToLogin() {
        webTestClient.get()
          .uri("/")
          .exchange()
          .expectStatus().is3xxRedirection();
    }

    @Test
    @WithMockUser
    void whenHasCredentials_thenSeesGreeting() {
        webTestClient.get()
          .uri("/")
          .exchange()
          .expectStatus().isOk()
          .expectBody(String.class).isEqualTo("Hello, user");
    }
}
