package com.baeldung.reactive.eventstreaming;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.net.URI;

public class CountWebSocketClient {
	WebSocketClient client = new ReactorNettyWebSocketClient();

	public Disposable receiveCount() {
		return client.execute(
				URI.create("ws://localhost:8080/event-emitter"),
				session -> session.send(
						Mono.just(session.textMessage("")))
						.thenMany(session.receive()
								.map(WebSocketMessage::getPayloadAsText)
								.log())
						.then())
				.subscribe();
	}
}