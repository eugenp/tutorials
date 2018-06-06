package com.baeldung.reactive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class InfiniteFluxController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfiniteFluxController.class);

    @GetMapping(path = "/numbers/{count}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> infiniteStream(@PathVariable("count") long count) {

        Flux<Long> generateFlux = Flux.generate(() -> 0L, (state, sink) -> {
            sink.next(state++);
            if (count > -1 && state > count) { //if count is less than 0 then the Flux is practically infinite
                sink.complete();
            }
            return state;
        });

        return generateFlux.delayElements(Duration.ofSeconds(1));
    }


    @GetMapping("/numbers")
    public HttpStatus self() {

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

        int countTillNumber = 5;

        webClient.get()
            .uri("/numbers/" + countTillNumber)
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .retrieve()
            .bodyToFlux(Long.class)
            .subscribe(number -> LOGGER.info(String.valueOf(number)),
                    (throwable -> LOGGER.error("An unexpected error occurred. See trace", throwable)),
                    () -> System.out.println("Flux data transfer complete"));
        return HttpStatus.OK;
    }

}
