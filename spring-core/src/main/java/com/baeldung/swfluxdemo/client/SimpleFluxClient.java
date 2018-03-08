package com.baeldung.swfluxdemo.client;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

/**
 * @author Robert B. Andrews 1.0 3/7/18
 *
 */
public class SimpleFluxClient {
	public static void main(String[] args) throws InterruptedException {
		WebClient webClient = WebClient.create("http://localhost:8080");
		Mono<String> result = webClient.get().retrieve().bodyToMono(String.class);

	}

}
