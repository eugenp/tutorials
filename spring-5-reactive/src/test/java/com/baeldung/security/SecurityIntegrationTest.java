package com.baeldung.security;

import com.baeldung.reactive.SpringSecurity5Application;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringSecurity5Application.class)
public class SecurityIntegrationTest {

    @Autowired
    ApplicationContext context;

    private WebTestClient rest;

    @Before
    public void setup() {
        this.rest = WebTestClient.bindToApplicationContext(this.context).configureClient().build();
    }

    @Test
    public void whenNoCredentials_thenRedirectToLogin() {
        this.rest.get().uri("/").exchange().expectStatus().is3xxRedirection();
    }

    @Test
    @Ignore
    @WithMockUser
    public void whenHasCredentials_thenSeesGreeting() {
        this.rest.get().uri("/").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("Hello, user");
    }
}
