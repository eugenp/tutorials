package com.baeldung.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.model.Stock;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/client/stocks")
public class StockRateClientController {

	private WebClient webClient = WebClient.create("http://localhost:8080/server/stocks");

	@GetMapping(value = "/{stockName}/live", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Stock> getStockRates(@PathVariable("stockName") final String stockName) {
		return webClient.get().uri("/{stockName}/live", stockName).retrieve().bodyToFlux(Stock.class);
	}

}
