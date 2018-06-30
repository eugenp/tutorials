package com.baeldung;

import java.time.Duration;
import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    Duration pushInterval = Duration.ofSeconds(1);

    @RequestMapping(path = "/{symbol}", method = RequestMethod.GET)
    Flux<Quote> subscribe(@PathVariable("symbol") String symbol) {
        return Flux.interval(pushInterval)
          .map(index -> new Quote(symbol, new Random().nextDouble()));
    }
}
