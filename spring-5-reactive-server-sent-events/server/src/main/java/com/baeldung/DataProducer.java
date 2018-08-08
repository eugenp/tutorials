package com.baeldung;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class DataProducer {

    @GetMapping(value = "/data", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> getData() {
        return Flux
          .fromStream(Stream.generate(() -> System.currentTimeMillis()))
          .delayElements(Duration.ofSeconds(1));
    }
}
