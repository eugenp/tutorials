package com.baeldung.functional;

import static org.springframework.web.reactive.function.BodyExtractors.toDataBuffers;
import static org.springframework.web.reactive.function.BodyExtractors.toFormData;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RootServlet extends ServletHttpHandlerAdapter {

    public RootServlet() {
        this(WebHttpHandlerBuilder.webHandler((WebHandler) toHttpHandler(routingFunction()))
            .filter(new IndexRewriteFilter())
            .build());
    }

    RootServlet(HttpHandler httpHandler) {
        super(httpHandler);
    }

    private static final Actor BRAD_PITT = new Actor("Brad", "Pitt");
    private static final Actor TOM_HANKS = new Actor("Tom", "Hanks");
    private static final List<Actor> actors = new CopyOnWriteArrayList<>(Arrays.asList(BRAD_PITT, TOM_HANKS));

    private static RouterFunction<?> routingFunction() {

        return route(GET("/test"), serverRequest -> ok().body(fromValue("helloworld"))).andRoute(POST("/login"), serverRequest -> serverRequest.body(toFormData())
            .map(MultiValueMap::toSingleValueMap)
            .map(formData -> {
                System.out.println("form data: " + formData.toString());
                if ("baeldung".equals(formData.get("user")) && "you_know_what_to_do".equals(formData.get("token"))) {
                    return ok().body(Mono.just("welcome back!"), String.class)
                        .block();
                }
                return ServerResponse.badRequest()
                    .build()
                    .block();
            }))
            .andRoute(POST("/upload"), serverRequest -> serverRequest.body(toDataBuffers())
                .collectList()
                .map(dataBuffers -> {
                    AtomicLong atomicLong = new AtomicLong(0);
                    dataBuffers.forEach(d -> atomicLong.addAndGet(d.asByteBuffer()
                        .array().length));
                    System.out.println("data length:" + atomicLong.get());
                    return ok().body(fromValue(atomicLong.toString()))
                        .block();
                }))
            .and(RouterFunctions.resources("/files/**", new ClassPathResource("files/")))
            .andNest(path("/actor"), route(GET("/"), serverRequest -> ok().body(Flux.fromIterable(actors), Actor.class)).andRoute(POST("/"), serverRequest -> serverRequest.bodyToMono(Actor.class)
                .doOnNext(actors::add)
                .then(ok().build())))
            .filter((request, next) -> {
                System.out.println("Before handler invocation: " + request.path());
                return next.handle(request);
            });

    }

}
