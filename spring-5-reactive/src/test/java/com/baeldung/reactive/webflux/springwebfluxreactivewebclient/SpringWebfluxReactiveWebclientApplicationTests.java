package com.spring.webflux.springwebfluxreactivewebclient;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.webfluxDemo.controller.SimpleWebFluxController;
import com.baeldung.webfluxDemo.service.SimpleWebFluxService;

import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SimpleWebFluxController.class, SimpleWebFluxService.class })
@WebFluxTest(controllers = SimpleWebFluxController.class)
public class SpringWebfluxReactiveWebclientApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void get() throws Exception {

		FluxExchangeResult<String> result = webClient.get().uri("/").accept(MediaType.TEXT_PLAIN).exchange()
				.returnResult(String.class);
		StepVerifier.create(result.getResponseBody()).expectSubscription().thenAwait(Duration.ofSeconds(1))
				.expectNextCount(0).thenAwait(Duration.ofSeconds(1)).expectNextCount(1);

	}

}
