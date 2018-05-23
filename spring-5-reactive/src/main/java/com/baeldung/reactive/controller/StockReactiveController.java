package com.baeldung.reactive.controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.Stock;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/rtes")
public class StockReactiveController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/stocks/{code}")
    public Flux<Stock> getStocks(@PathVariable String code) {
        BigDecimal startingPrice = new BigDecimal("100");
        Flux<Stock> stockFlux = Flux.fromStream(Stream.generate(() -> new Stock(
                        code,
                        new Random().nextBoolean() ? 
                                startingPrice.add(BigDecimal.valueOf(new Random().nextDouble())): 
                                startingPrice.subtract(BigDecimal.valueOf(new Random().nextDouble())))));
        Flux<Long> emmitFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(stockFlux, emmitFlux).map(Tuple2::getT1);
    }

    public static void main(String [] args) {

    }
}
