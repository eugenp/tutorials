package com.baeldung.reactive.webflux;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class BookController {

    @GetMapping(value = "/books", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Book> findAllUsers() {

        Flux<Long> emitInterval = Flux.interval(Duration.ofSeconds(1));
        Flux<Book> booksFlux = Flux.fromStream(Stream.generate(() -> new Book(new Random().nextLong(), randomAlphabetic(12))));
        return Flux.zip(booksFlux, emitInterval)
            .map(Tuple2::getT1);

    }

}
