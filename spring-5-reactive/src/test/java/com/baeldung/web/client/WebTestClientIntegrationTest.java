package com.baeldung.web.client;

import com.baeldung.reactive.Spring5ReactiveApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5ReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTestClientIntegrationTest {

    @LocalServerPort
    private int port;

    private final RouterFunction ROUTER_FUNCTION = RouterFunctions.route(RequestPredicates.GET("/resource"), request -> ServerResponse.ok()
        .build());
    private final WebHandler WEB_HANDLER = exchange -> Mono.empty();

    @Test
    public void testWebTestClientWithServerWebHandler() {
        WebTestClient.bindToWebHandler(WEB_HANDLER)
            .build();
    }

    @Test
    public void testWebTestClientWithRouterFunction() {
        WebTestClient.bindToRouterFunction(ROUTER_FUNCTION)
            .build()
            .get()
            .uri("/resource")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .isEmpty();
    }

    @Test
    public void testWebTestClientWithServerURL() {
        WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + port)
            .build()
            .get()
            .uri("/resource")
            .exchange()
            .expectStatus()
            .is3xxRedirection()
            .expectBody();
    }

}
