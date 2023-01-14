package com.baeldung.spring.reactive.springreactiveexceptions.client;

import com.baeldung.spring.reactive.springreactiveexceptions.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class DemoSelfClient {

	@Autowired
	@Qualifier("webClient")
	private WebClient webClient;
	@Autowired
	private ObjectMapper objectMapper;

	public Mono<Users> fetch() {
		return webClient
		  .post()
		  .uri("/1.0/process")
		  .body(BodyInserters.fromPublisher(readRequestBody(), Users.class))
		  .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Users.class));
	}

	private Mono<Users> readRequestBody() {
		return Mono
		  .fromCallable(() -> objectMapper.readValue(new ClassPathResource("390KB.json")
			.getURL(), Users.class))
		  .subscribeOn(Schedulers.boundedElastic());
	}
}
