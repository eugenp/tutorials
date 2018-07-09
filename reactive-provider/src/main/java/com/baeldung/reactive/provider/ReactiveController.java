package com.baeldung.reactive.provider;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class ReactiveController {

    private static Random random = new Random();

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/stockprice")
    Flux<StockPriceEvent> stockPrice() {
        Flux<StockPriceEvent> eventFlux = 
            Flux.fromStream(Stream.generate(() -> new StockPriceEvent(random.nextInt(100), new Date())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux)
            .map(Tuple2::getT1);

    }

}
