package com.manoj.webFlux;

import com.manoj.webFlux.dto.TimeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebFluxApplicationTests {

	private WebTestClient webTestClient;

	@Before
	public void before(){
		this.webTestClient
				= WebTestClient.bindToServer().baseUrl("http://localhost:8080").responseTimeout(Duration.ofSeconds(20)).build();
	}

	@Test
	public void getTimeTokenMax(){
		this.webTestClient.get()
				.uri("/timetokens?max=8")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(TimeToken.class)
				.hasSize(8);
	}

	@Test
	public void getTimeTokens() {

		this.webTestClient.get()
				.uri("/timetokens")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(TimeToken.class)
				.hasSize(10);
	}

	@Test
	public void getmonoTimeToken(){
		this.webTestClient.get()
				.uri("timetokens/25")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(TimeToken.class)
				.hasSize(1);
	}

}
