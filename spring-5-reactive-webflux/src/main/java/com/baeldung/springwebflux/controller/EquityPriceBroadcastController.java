package com.baeldung.springwebflux.controller;

import com.baeldung.springwebflux.model.Equity;
import com.baeldung.springwebflux.repository.DataRepository;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RestController
@RequestMapping(path = "/api")
public class EquityPriceBroadcastController {

    private Scheduler scheduler = Schedulers.newSingle("EventThrower");

    private Mono<Equity> createEquityInstance() {
        return Mono.fromCallable(DataRepository::generateEquityPriceChanges)
            .subscribeOn(scheduler);
    }

    @GetMapping(path = "/getEquityPrice", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Equity> broadcastEquityPrices(ServerHttpResponse response) {
        return createEquityInstance().delayElement(Duration.ofSeconds(1))
            .repeat();
    }

}
