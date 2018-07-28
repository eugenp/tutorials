package com.baeldung.reactive.urlmatch;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.ipc.netty.NettyContext;
import reactor.ipc.netty.http.server.HttpServer;

public class ExploreSpring5URLPatternUsingRouterFunctions {

    private RouterFunction<ServerResponse> routingFunction() {

        return route(GET("/p?ths"), serverRequest -> ok().body(fromObject("/p?ths"))).andRoute(GET("/test/{*id}"), serverRequest -> ok().body(fromObject(serverRequest.pathVariable("id"))))
            .andRoute(GET("/*card"), serverRequest -> ok().body(fromObject("/*card path was accessed")))
            .andRoute(GET("/{var1}_{var2}"), serverRequest -> ok().body(fromObject(serverRequest.pathVariable("var1") + " , " + serverRequest.pathVariable("var2"))))
            .andRoute(GET("/{baeldung:[a-z]+}"), serverRequest -> ok().body(fromObject("/{baeldung:[a-z]+} was accessed and baeldung=" + serverRequest.pathVariable("baeldung"))))
            .and(RouterFunctions.resources("/files/{*filepaths}", new ClassPathResource("files/")));
    }

    NettyContext start() throws Exception {
        WebHandler webHandler = (WebHandler) toHttpHandler(routingFunction());
        HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler(webHandler)
            .filter(new IndexRewriteFilter())
            .build();

        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("localhost", 9090);
        return server.newHandler(adapter).block();

    }

    public static void main(String[] args) {
        try {
            new FunctionalWebApplication().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
