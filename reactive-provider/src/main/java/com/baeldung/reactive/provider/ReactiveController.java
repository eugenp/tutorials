package com.baeldung.reactive.provider;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class ReactiveController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/integers")
    Flux<Integer> integers() {
        Flux<Integer> integers = Flux.range (1,3);
        Flux<Long> timelag = Flux.interval(Duration.ofSeconds(2));
        return Flux.zip(integers, timelag)
            .map(Tuple2::getT1);

    }

}
