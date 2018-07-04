package com.example.springwebflux.client;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springwebflux.events.Train;

import reactor.core.Disposable;

@SpringBootApplication
public class SpringWebfluxClientApp {

	@Bean
	WebClient client() {
		return WebClient.create("http://localhost:8080");
	}

	@Bean
	Disposable demoClient(WebClient client) {
		return client().get().uri("/trainschedule").accept(MediaType.TEXT_EVENT_STREAM).exchange()
				.flatMapMany(response -> response.bodyToFlux(Train.class)).subscribe(System.out::println);
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringWebfluxClientApp.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "7777"));
		app.run(args);

	}

}
