package com.example.springwebflux.server;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.springwebflux.events.Train;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class SpringWebfluxServerApp {

	@GetMapping("/trainschedule/{trainName}")
	Mono<Train> getTrainSchByName(@PathVariable String trainName) {
		return Mono.just(new Train(trainName, new Date()));
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/trainschedule")
	Flux<Train> getTrainSch() {
		Flux<Train> trainFlux = Flux.fromStream(Stream.generate(() -> new Train("Heartbeat: ", new Date())));
		Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
		return Flux.zip(trainFlux, durationFlux).map(t -> t.getT1());
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxServerApp.class, args);
	}
}
