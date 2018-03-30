package com.springframework.guru.webfluxstreaming;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebfluxStreamingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxStreamingApplication.class, args);
	}
	
	@Value("${stream-app-uri:http://localhost:8080/getStreamEvent}")
	public String streamAppUri;

	@Override
	public void run(String... args) throws Exception {
		WebClient
		.create(streamAppUri) // Set the URI
		.get() // The Http Method
		.accept(MediaType.APPLICATION_STREAM_JSON) // Accept Header with Stream JSON
		.retrieve() // Rest Call is invoked here
		.bodyToFlux(Integer.class) // bodyToFlux because Stream returns multiple values
		.subscribe(System.out::println); // Received Data is obtained from flux here 
	}
}
