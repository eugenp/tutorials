package com.stock.prices.stockprices.controller;

import java.time.Duration;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stock.prices.stockprices.model.StockPrice;
import com.stock.prices.stockprices.repository.StockPriceRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class StockPriceController {

		@Autowired
	    private StockPriceRepository stockPriceRepository;

	    @GetMapping("/stockprices")
	    public Flux<StockPrice> getAllPrices() {
	        return stockPriceRepository.findAll();
	    }

	    @PostMapping("/stockprices")
	    public Mono<StockPrice> createStockPrice(@Valid @RequestBody StockPrice stockprice) {
	        return stockPriceRepository.save(stockprice);
	    }
	    
	    @GetMapping(value = "/stockprices/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	    public Flux<StockPrice> getStockPrice() {
	        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));

	        Flux<StockPrice> stockFlux = Flux.fromStream(Stream.generate(() ->new StockPrice("TempId", "TempName",(int) (Math.random() * (50 - 10)) + 10 )));

	        return Flux.zip(stockFlux, durationFlux).map(Tuple2::getT1);
	    }

}
