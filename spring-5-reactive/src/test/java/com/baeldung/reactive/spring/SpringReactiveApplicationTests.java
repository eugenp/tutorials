package com.baeldung.reactive.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class SpringReactiveApplicationTests {

	@Test
	public void testGetMessages() {
		
		WebTestClient webTestClient = WebTestClient
				.bindToController(new ReactiveController()).build();

		FluxExchangeResult<String> result = webTestClient.get().uri("/messages")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus().isOk()
				.returnResult(String.class);
		
		Flux<String> messageFlux = result.getResponseBody();
		
		StepVerifier.create(messageFlux)
				.expectNextCount(1)
				.consumeNextWith(System.out::println)
				.thenCancel()
				.verify();
	}

}
