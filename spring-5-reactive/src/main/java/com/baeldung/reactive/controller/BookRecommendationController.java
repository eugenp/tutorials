package com.baeldung.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static reactor.core.publisher.Flux.fromStream;

@RestController
@RequestMapping("/books")
public class BookRecommendationController {

    public static final List<String> BOOK_NAMES = Arrays.asList("Fellowship of the ring",
      "Return of the king",
      "The two towers",
      "Harry potter",
      "Game of thrones");

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, path = "/names")
    public Flux<String> getRecommendedBookNames() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<String> randomBookNames = fromStream(Stream.generate(this::randomBookNames));
        return Flux.zip(interval, randomBookNames).map(Tuple2::getT2);
    }

    private String randomBookNames() {
        return BOOK_NAMES.get(new Random().nextInt(BOOK_NAMES.size()));
    }
}
