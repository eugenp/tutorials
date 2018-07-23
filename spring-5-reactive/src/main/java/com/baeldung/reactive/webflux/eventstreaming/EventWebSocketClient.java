package com.baeldung.reactive.webflux.eventstreaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

public class EventWebSocketClient {

	private static final String REMOTE_URL = "ws://localhost:8080/events";

    public static void main(String[] args) throws URISyntaxException {
		
		WebSocketClient client = new ReactorNettyWebSocketClient();
		
		client.execute(
		          URI.create(REMOTE_URL), 
		          session -> session.receive()
		              .map(WebSocketMessage::getPayloadAsText)
		              .log().then())
		            .block(Duration.ofMinutes(10L));
	}
}
