package com.baeldung.reactive.eventstreaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Disposable;

@SpringBootApplication
public class SpringWebFluxEventStreamingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxEventStreamingApplication.class, args);

		CountWebSocketClient webSocketClient = new CountWebSocketClient();
		Disposable disposable = webSocketClient.receiveCount();
		Runtime.getRuntime().addShutdownHook(new Thread(disposable::dispose));

	}
}
