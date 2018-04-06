package com.springWebClient.SpringWebClienteventStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringWebClientEventStreamApplication {

	@Bean
	WebClient webClient() {
		return WebClient.create("http://localhost:8099/student");
	}


	@Bean
	CommandLineRunner commandLineRunner(WebClient webClient) {
		return strings -> {

			webClient
					.get()
					.uri("/all")
					.retrieve()
					.bodyToFlux(Student.class)
					.flatMap(stud -> {
						return webClient.get()
								.uri("/{id}/events", stud.getId())
								.retrieve()
								.bodyToFlux(StudentDataEvent.class);


					})
					.subscribe(System.out::println);

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringWebClientEventStreamApplication.class, args);
	}
}
