package com.baeldung.messages;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class MessageController {

	@GetMapping("/messages")
	public Flux<Message> getAllMessages() {
		return Flux
				.fromStream(Stream.generate(() -> createRandomMessage()))
				.delayElements(Duration.ofSeconds(1));
	}

	private Message createRandomMessage() {
		int id = new Random().nextInt();
		String message = "Generating " + id + " message";
		return new Message(id, message, "user1", System.currentTimeMillis());
	}
}
