package com.baeldung.server;

import com.baeldung.model.Data;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@SpringBootApplication @RestController public class WebfluxServer {

    //text event example
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/data") Flux<Data> data() {
        Flux<Data> eventFlux = Flux.fromStream(Stream.generate(() ->
          new Data(UUID.randomUUID().toString(), new Date())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebfluxServer.class, args);
    }
}
