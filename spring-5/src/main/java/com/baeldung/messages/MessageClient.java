package com.baeldung.messages;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class MessageClient {

	public void consumeMessages() {

		Flux<Message> messages = WebClient //@formatter:off
									.create("http://localhost:8080/messages")
									.get()
									.accept(MediaType.APPLICATION_JSON)
									.retrieve()
									.bodyToFlux(Message.class);
		//@formatter:on
		messages.log();
	}
}
