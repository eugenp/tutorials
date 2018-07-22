package com.baeldung.reactivebackpressuredemo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactivebackpressuredemo.model.Foo;
import com.baeldung.reactivebackpressuredemo.repository.FooTraffic;

import reactor.core.publisher.Mono;

@Component
public class FooHandler {

    @Autowired
    FooTraffic fooTraffic;

    public Mono<ServerResponse> createNewFooResource(ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(fooTraffic.flowTraffic(), Foo.class);

    }

}
