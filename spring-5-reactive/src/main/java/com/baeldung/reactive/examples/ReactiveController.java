package com.baeldung.reactive.examples;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/example")
public class ReactiveController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/counter")
    public Flux<Long> counter() {
        return Flux.interval(Duration.ofSeconds(1));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/time")
    public Flux<DateTimeDto> time() {
        final Flux<DateTimeDto> dateTimeFlux = Flux.fromStream(Stream.generate(DateTimeDto::new));
        final Flux<Long> emmitFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(dateTimeFlux, emmitFlux)
            .map(Tuple2::getT1);
    }

    public static class DateTimeDto {

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dateTime = LocalDateTime.now();

    }

}
