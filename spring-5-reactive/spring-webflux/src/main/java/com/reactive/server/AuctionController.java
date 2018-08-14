package com.reactive.server;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class AuctionController {

    Logger logger = LoggerFactory.getLogger(AuctionController.class);

    @GetMapping(value = "/bids", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getBid() {
        Random r = new Random();
        int startPrice = 5;
        int endPrice = 1000;
        int auctionDuration = 120;

        return Flux.fromIterable(r.ints(startPrice, endPrice)
            .distinct()
            .limit(auctionDuration)
            .sorted()
            .boxed()
            .collect(Collectors.toList()))
            .map(s -> Integer.valueOf(s))
            .delayElements(Duration.ofSeconds(1));

    }
}
