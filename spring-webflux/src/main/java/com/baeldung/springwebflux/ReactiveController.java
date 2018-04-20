package com.baeldung.springwebflux;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

import java.time.*;
import java.util.stream.*;

@RestController
public class ReactiveController {

    @GetMapping(value = "/helloEvents", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<String> helloEvents() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Integer> range = Flux.fromStream(Stream.iterate(0, i -> i + 2));
        return Flux.zip(interval, range).map(t -> "Message in range number : " + t.getT2());
    }
}
