package com.baeldung.reactive.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import com.baeldung.reactive.model.Stock;

public class StockClient {

	public void getStockUpdates(String stockCode) {
		WebClient client = WebClient.create("localhost:9111");
		RequestHeadersSpec<?> request = client.get().uri("/rtes/stocks/"+stockCode).accept(MediaType.TEXT_EVENT_STREAM);
		request.retrieve().bodyToFlux(Stock.class).toStream().forEach(System.out::println);
	}

	public static void main(String[] args) throws InterruptedException {
		new StockClient().getStockUpdates("GOOGL");

		while(true) {
			Thread.sleep(1000L);
		}
	}
}
