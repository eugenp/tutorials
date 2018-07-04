package com.example.springwebflux.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringWebfluxServerAppTest {
	
	private WebTestClient webTestClient;
	
	@Before
	public void before() {
		this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
	}

	@Test
	public void testGetTrainSchByName() {
		this.webTestClient.get()
		.uri("/trainschedule/Heartbeat:")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk();
	}

}
