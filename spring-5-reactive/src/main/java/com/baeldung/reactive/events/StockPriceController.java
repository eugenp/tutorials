package com.baeldung.reactive.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.reactive.events.StockPriceService;
import reactor.core.publisher.Flux;

@RestController
public class StockPriceController 
{
	@Autowired
	StockPriceService stockPriceService;

	@GetMapping(
	value = "/stockprice",
	produces = MediaType.TEXT_EVENT_STREAM_VALUE
	)
	public Flux<String> getStockPrice()
	{
		return stockPriceService.getStockPrice();
	}
}
