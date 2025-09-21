package com.baeldung.spring.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockitoBean(types = ChristmasJoyClient.class)
class HelloWorldApiV4IntegrationTest {

    RestTestClient client;

    @Autowired
    ChristmasJoyClient christmasJoy;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        client = RestTestClient.bindToApplicationContext(context)
            .build();
    }

    @Test
    void shouldFetchHello() {
        Mockito.when(christmasJoy.getRandomGreeting())
            .thenReturn("Joy to the World");
        client.get()
            .uri("/api/v4/hello")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
            .expectBody(String.class)
            .consumeWith(message -> assertThat(message.getResponseBody()).isEqualTo("Joy to the World"));
    }

}
