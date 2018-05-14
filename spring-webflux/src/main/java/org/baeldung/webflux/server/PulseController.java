package org.baeldung.webflux.server;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class PulseController {

    @GetMapping("/pulsate")
    public Flux<ServerSentEvent<Integer>> pulsate() {
        return Flux
          .interval(Duration.ofSeconds(1))
          .map(sequence -> Tuples.of(sequence, ThreadLocalRandom
            .current()
            .nextInt()))
          .map(data -> ServerSentEvent.<Integer> builder()
            .event("heartbeat")
            .id(Long.toString(data.getT1()))
            .data(data.getT2())
            .build());
    }
}
