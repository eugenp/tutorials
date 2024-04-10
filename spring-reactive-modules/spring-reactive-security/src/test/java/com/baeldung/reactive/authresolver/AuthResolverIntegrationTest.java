package com.baeldung.reactive.authresolver;

import java.util.Base64;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {AuthResolverApplication.class, AuthResolverController.class, CustomWebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureWebTestClient(timeout = "36000000")
public class AuthResolverIntegrationTest {
    @Autowired
    private WebTestClient testClient;

    @Test
    public void givenCustomerCredential_whenWelcomeCustomer_thenExpectOk() {
        testClient
          .get()
          .uri("/customer/welcome")
          .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("customer1:pass1".getBytes()))
          .exchange()
          .expectStatus()
          .isOk();
    }

    @Test
    public void givenEmployeeCredential_whenWelcomeCustomer_thenExpect401Status() {
        testClient
          .get()
          .uri("/customer/welcome")
          .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("employee1:pass1".getBytes()))
          .exchange()
          .expectStatus()
          .isUnauthorized();
    }

    @Test
    public void givenEmployeeCredential_whenWelcomeEmployee_thenExpectOk() {
        testClient
          .get()
          .uri("/employee/welcome")
          .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("employee1:pass1".getBytes()))
          .exchange()
          .expectStatus()
          .isOk();
    }

    @Test
    public void givenCustomerCredential_whenWelcomeEmployee_thenExpect401Status() {
        testClient
          .get()
          .uri("/employee/welcome")
          .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("customer1:pass1".getBytes()))
          .exchange()
          .expectStatus()
          .isUnauthorized();
    }
}
