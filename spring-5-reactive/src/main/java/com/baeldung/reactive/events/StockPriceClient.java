package com.baeldung.reactive.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(scanBasePackages = { "com.baeldung.reactive.events" })
public class StockPriceClient{

	Logger logger = LoggerFactory.getLogger(StockPriceClient.class);

	@Bean
	WebClient getWebClient() 
	{
		return WebClient.create("http://localhost:8084");
	}

	@Bean
	CommandLineRunner demo(WebClient client) 
	{
		return args -> {
			client.get()
			.uri("/stockprice")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.exchange()
			.flatMapMany(res -> res.bodyToFlux(String.class))
			.subscribe(StockPriceClient::handleResponse);
		};
	}

	private static void handleResponse(String s) 
	{
		System.out.println("Response Handler");
		System.out.println(s);
	}

	public static void main(String[] args) 
	{
		new SpringApplicationBuilder(StockPriceClient.class).properties(java.util.Collections.singletonMap("server.port", "8084"))
		.run(args);
	}

}