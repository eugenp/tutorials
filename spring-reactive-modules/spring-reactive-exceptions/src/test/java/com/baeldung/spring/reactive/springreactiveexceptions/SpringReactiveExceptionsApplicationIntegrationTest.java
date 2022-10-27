package com.baeldung.spring.reactive.springreactiveexceptions;

import com.baeldung.spring.reactive.springreactiveexceptions.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=60291")
class SpringReactiveExceptionsApplicationIntegrationTest {
	@Autowired
	private WebTestClient webTestClient;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void givenARequestBody_whenProcessIsCalled_thenReturnTheBodyAsIs() throws IOException {
		Users users = objectMapper.readValue(new ClassPathResource("390KB.json")
		  .getURL(), Users.class);

		webTestClient
		  .post()
		  .uri("1.0/process")
		  .body(BodyInserters.fromValue(users))
		  .exchange()
		  .expectStatus()
		  .isEqualTo(HttpStatus.OK)
		  .expectBody(Users.class)
		  .isEqualTo(users);
	}

	@Test
	void whenTriggerIsCalled_thenReturnTheExpectedJSONResponse() throws IOException {
		Users users = objectMapper.readValue(new ClassPathResource("390KB.json")
		  .getURL(), Users.class);

		webTestClient
		  .post()
		  .uri("1.0/trigger")
		  .exchange()
		  .expectStatus()
		  .isEqualTo(HttpStatus.OK)
		  .expectBody(Users.class)
		  .isEqualTo(users);
	}
}