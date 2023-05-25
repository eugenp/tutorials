package com.baeldung.security.opa.controller;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

// !!! NOTICE: Start OPA server before running this test class !!!
@SpringBootTest
@ActiveProfiles("test")
class AccountControllerLiveTest {

    @Autowired
    ApplicationContext context;
    WebTestClient rest;

    @BeforeEach
    public void setup() {
        this.rest = WebTestClient.bindToApplicationContext(this.context)
          .apply(springSecurity())
          .configureClient()
          .build();
    }
    

    @Test
    @WithMockUser(username = "user1", roles = { "account:read:0001"} )
    void testGivenValidUser_thenSuccess() {
        rest.get()
         .uri("/account/0001")
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .is2xxSuccessful();
    }

    @Test
    @WithMockUser(username = "user1", roles = { "account:read:0002"} )
    void testGivenValidUser_thenUnauthorized() {
        rest.get()
         .uri("/account/0001")
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isForbidden();
    }
    
    @Test
    @WithMockUser(username = "user1", roles = {} )
    void testGivenNoAuthorities_thenForbidden() {
        rest.get()
          .uri("/account/0001")
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isForbidden();
    }
    
    
}
