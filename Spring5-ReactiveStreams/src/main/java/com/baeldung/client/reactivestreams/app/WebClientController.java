package com.baeldung.client.reactivestreams.app;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebClientController {
	
	public static void main(String args[]) {
		new SpringApplicationBuilder(WebClientController.class).properties(Collections.singletonMap("server.port", "8081")).run(args);
	}
	
	@Bean
	CommandLineRunner cmdDemo() {
		WebClient client = WebClient.create();
		
		return args -> {

		    client.get().uri("http://localhost:8080/allevents")
		   			.accept(MediaType.TEXT_EVENT_STREAM)
		    		.exchange().flatMapMany(x->x.bodyToFlux(String.class)).subscribe(System.out::println);
		};
	}
	

}
