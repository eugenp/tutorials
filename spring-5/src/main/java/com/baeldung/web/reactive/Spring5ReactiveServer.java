package com.baeldung.web.reactive;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

public class Spring5ReactiveServer {

    public static void main(String [] args) throws Exception {
        RouterFunction<?> route = RouterFunctions.route(POST("/task/process")
          ,request -> ServerResponse.ok()
          .body(request.bodyToFlux(Task.class)
          .map(ll -> new Task("TaskName", 1)), Task.class))
          .and(RouterFunctions.route(GET("/task")
          ,request -> ServerResponse.ok().body(Mono.just("server is alive")
          , String.class)));
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("localhost", 8080);
        server.newHandler(adapter).block();
        System.in.read();
    }
}
