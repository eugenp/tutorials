package com.baeldung.reactive.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = WebClientApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class WebTestClientIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private WebClientController controller;

    @Test
    void whenBindToWebHandler_thenRequestProcessed() {
        WebHandler webHandler = exchange -> Mono.empty();

        WebTestClient.bindToWebHandler(webHandler)
          .build()
          .get()
          .exchange()
          .expectBody().isEmpty();
    }

    @Test
    void whenBindToRouter_thenRequestProcessed() {
        RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(
          RequestPredicates.GET("/resource"),
          request -> ServerResponse.ok().build()
        );

        WebTestClient.bindToRouterFunction(routerFunction)
          .build()
          .get().uri("/resource")
          .exchange()
          .expectStatus().isOk()
          .expectBody().isEmpty();
    }

    @Test
    @WithMockUser
    void whenBindToServer_thenRequestProcessed() {
        WebTestClient.bindToServer()
          .baseUrl("http://localhost:" + port).build()
          .get().uri("/resource")
          .exchange()
          .expectStatus().isOk()
          .expectBody().jsonPath("field").isEqualTo("value");
    }

    @Test
    @WithMockUser
    void whenBindToApplicationContext_thenRequestProcessed() {
        WebTestClient.bindToApplicationContext(context)
          .build()
          .get().uri("/resource")
          .exchange()
          .expectStatus().isOk()
          .expectBody().jsonPath("field").isEqualTo("value");
    }

    @Test
    void whenBindToController_thenRequestProcessed() {
        WebTestClient.bindToController(controller)
          .build()
          .get().uri("/resource")
          .exchange()
          .expectStatus().isOk()
          .expectBody().jsonPath("field").isEqualTo("value");
    }
}
