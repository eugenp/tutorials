package com.baeldung.reactive.eventstreaming;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

public class EventConsumerWebClient {

	private WebClient webClient = WebClient.builder()
		.baseUrl("http://localhost:8080")
		.build();

	public static void main(String[] args) {
		EventConsumerWebClient eventConsumerWebClient = new EventConsumerWebClient();
		Disposable disposable = eventConsumerWebClient.eventNoticed();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			disposable.dispose();
		}
	}

	public Disposable eventNoticed() {
		return webClient.get()
			.uri("/events")
			.accept(MediaType.APPLICATION_STREAM_JSON)
			.exchange()
			.publishOn(Schedulers.single())
			.flatMapMany(response -> response.bodyToFlux(Event.class))
			.subscribe(s -> System.out.println(s.toString()));
	}
}
