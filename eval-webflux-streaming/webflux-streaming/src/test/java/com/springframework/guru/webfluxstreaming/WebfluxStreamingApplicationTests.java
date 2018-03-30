package com.springframework.guru.webfluxstreaming;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebfluxStreamingApplicationTests {
	
	@Autowired
	public WebTestClient webTestClient;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void receiveData() {
		webTestClient
		.get()
		.uri("/getStreamEvent")
		.accept(MediaType.APPLICATION_STREAM_JSON)
		.exchange()
		.returnResult(Integer.class)
		.getResponseBody()
		.subscribe(System.out::println);
	}

}
