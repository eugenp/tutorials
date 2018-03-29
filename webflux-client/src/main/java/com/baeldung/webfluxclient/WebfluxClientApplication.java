package com.baeldung.webfluxclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebfluxClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxClientApplication.class, args);
	}

	@Bean
	public WebClient client() {
	    return WebClient.create("http://localhost:8080");
	}
	
	@Bean
	public CommandLineRunner test(WebClient client) {
	    
	    return args -> {
	        client.get().uri("/tweets")
	        .accept(MediaType.TEXT_EVENT_STREAM)
	        .exchange().flatMapMany(cr -> cr.bodyToFlux(String.class))
	        .subscribe(System.out::println);
	    };
	}
}
