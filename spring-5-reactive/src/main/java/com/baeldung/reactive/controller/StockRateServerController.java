package com.baeldung.reactive.controller;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.Stock;
import com.baeldung.reactive.service.StockRateService;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/server/stocks")
public class StockRateServerController {

        @Autowired
        StockRateService stockRateService;

        @GetMapping(value = "/{stockName}/live", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<Stock> getLiveStockRates(@PathVariable("stockName") String stockName) {
                Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
                Flux<Stock> liveStockRates = Flux.fromStream(Stream.generate(() -> stockRateService.getLiveStockRates(stockName)));
                return Flux.zip(intervalFlux, liveStockRates).map(Tuple2::getT2);
        }

}

