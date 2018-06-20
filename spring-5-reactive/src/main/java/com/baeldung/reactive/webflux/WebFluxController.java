package com.baeldung.reactive.webflux;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class WebFluxController {
	
    @GetMapping("/webflux")
    public Flux<String> getIntervalFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(tick -> "tick "+tick);
    }

}
