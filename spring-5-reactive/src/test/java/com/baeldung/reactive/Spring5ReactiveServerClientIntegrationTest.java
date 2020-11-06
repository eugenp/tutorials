package com.baeldung.reactive;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.web.reactive.Task;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class Spring5ReactiveServerClientIntegrationTest {
    private static DisposableServer disposableServer;
    private static WebTestClient webTestClient;

    @BeforeAll
    public static void setUp() throws Exception {
        HttpServer server = HttpServer.create()
            .host("localhost")
            .port(8080);
        RouterFunction<?> route = RouterFunctions.route(POST("/task/process"), request -> ServerResponse.ok()
            .body(request.bodyToFlux(Task.class)
                .map(ll -> new Task("TaskName", 1)), Task.class))
            .and(RouterFunctions.route(GET("/task"), request -> ServerResponse.ok()
                .body(Mono.just("server is alive"), String.class)));
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        disposableServer = server.handle(adapter)
            .bindNow();
        webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build();
    }

    @AfterAll
    public static void shutDown() {
        disposableServer.disposeNow();
    }

    @Test
    public void givenCheckTask_whenServerHandle_thenServerResponseALiveString() throws Exception {
        webTestClient.get()
            .uri("/task")
            .exchange()
            .expectBody(String.class);
    }

    @Test
    public void givenThreeTasks_whenServerHandleTheTasks_thenServerResponseATask() throws Exception {
        webTestClient.post()
            .uri("/task/process")
            .body(getLatLngs(), Task.class)
            .exchange()
            .expectBodyList(Task.class);
    }

    private static Flux<Task> getLatLngs() {
        return Flux.range(0, 3)
            .zipWith(Flux.interval(Duration.ofSeconds(1)))
            .map(x -> new Task("taskname", 1))
            .doOnNext(ll -> System.out.println("Produced: {}" + ll));
    }
}
