package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.Foo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FooController {

    private static final Flux<Foo> FLUX = Flux
      .interval(Duration.ofSeconds(1))
      .map(l -> new Foo(l, "foo"))
      .publish()
      .autoConnect();

    @GetMapping(value = "/someotherfoos", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Foo> subscribe() {
        return FLUX;
    }

}
