package com.baeldung.reactive.webflux.eval;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/purchases")
public class PurchaseReactiveServer {
    @GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Purchase> purchases() {
        return Flux.zip(
            Flux.fromStream(
                Stream.generate(
                    () -> new Purchase(
                        String.valueOf(System.currentTimeMillis()),
                        new Date()
                    )
                )
            ),
            Flux.interval(Duration.ofSeconds(1))
        ).map(Tuple2::getT1);
    }
}
