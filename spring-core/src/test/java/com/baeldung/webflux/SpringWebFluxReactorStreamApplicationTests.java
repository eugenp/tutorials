package com.baeldung.webflux;

import static org.junit.Assert.assertNotSame;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient(timeout = "10000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringWebFluxReactorStreamApplicationTests {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void givenWebTestClient_whenTextEventStream_thenCompatible() {
		webTestClient
			.get().uri("/check")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.exchange()
			.expectHeader()
			.contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM);
	}
	
	@Test
	public void givenWebTestClient_whenApplicationJson_then404() {
		
		webTestClient
			.get().uri("/check")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().is4xxClientError();
	}
	
	@Test
	public void whenGetFirstTwoEvents_thenTimeNotSame() {
		
		List<String> listOfFirstTwoEvents = webTestClient
			.get().uri("/check")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.exchange()
			.returnResult(String.class)
			.getResponseBody()
			.take(2)
			.collectList().block();
		
		String firstEvent= listOfFirstTwoEvents.get(0);
		String SecondEvent= listOfFirstTwoEvents.get(1);
		String firstEventTime= firstEvent.substring(firstEvent.length()-17, firstEvent.length()-9);
		String secondEventTime= SecondEvent.substring(SecondEvent.length()-17, SecondEvent.length()-9);
		assertNotSame(firstEventTime, secondEventTime);
	}
}

