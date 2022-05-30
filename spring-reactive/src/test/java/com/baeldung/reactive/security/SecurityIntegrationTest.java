package com.baeldung.reactive.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringSecurity5Application.class)
public class SecurityIntegrationTest {

    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToApplicationContext(context)
          .configureClient()
          .build();
    }

    @Test
    public void whenNoCredentials_thenRedirectToLogin() {
        webTestClient.get()
          .uri("/")
          .exchange()
          .expectStatus().is3xxRedirection();
    }

    @Test
    @WithMockUser
    public void whenHasCredentials_thenSeesGreeting() {
        webTestClient.get()
          .uri("/")
          .exchange()
          .expectStatus().isOk()
          .expectBody(String.class).isEqualTo("Hello, user");
    }
}
