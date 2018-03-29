package com.baeldung.stockexchange;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StockExchangeWebClient
{
	private final WebClient webClient = WebClient.create("http://localhost:8080");

	private final Mono<ClientResponse> stocks = webClient.get()
			.uri("/stock/{stockId}", 0)
			.accept(MediaType.TEXT_EVENT_STREAM)
			.exchange();

	public String getStocks()
	{
		return stocks.flatMap(stock -> stock.bodyToMono(String.class)).block();
	}
}
