package com.baeldung.reactive.webflux.eventstreaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

public class EventWebSocketClient {

	public static void main(String[] args) throws URISyntaxException {
		
		WebSocketClient client = new ReactorNettyWebSocketClient();
		
		client.execute(
		          URI.create("ws://localhost:8080/events"), 
		          session -> session.receive()
		              .map(WebSocketMessage::getPayloadAsText)
		              .log().then())
		            .block(Duration.ofMinutes(10L));
	}
}
