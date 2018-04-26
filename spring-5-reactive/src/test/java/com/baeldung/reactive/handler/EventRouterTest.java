package com.baeldung.reactive.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.reactive.model.Event;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventRouterTest {
	
	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void whenGetEventsUrlIsCalled_thenEventFluxIsReturned() {
	  FluxExchangeResult<Event> result = webTestClient
	    .get().uri("/events")
	    .accept(MediaType.TEXT_EVENT_STREAM)
	    .exchange()
	    .expectStatus().isOk()
	    .returnResult(Event.class);

	  Flux<Event> eventFlux = result.getResponseBody();

	  StepVerifier.create(eventFlux)
	    .expectNextCount(10)
	    .thenCancel()
	    .verify();
	}
}
