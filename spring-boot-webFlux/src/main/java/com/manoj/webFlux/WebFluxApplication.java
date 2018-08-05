package com.manoj.webFlux;

import com.manoj.webFlux.dto.TimeToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class WebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxApplication.class, args);
	}

	@GetMapping("/timetokens/{id}")
	Mono<TimeToken> getTimeTokenById(@PathVariable long id) {
		return Mono.just(new TimeToken(id, "Mono-Token-" + System.currentTimeMillis()));
	}

	@GetMapping(path = "/timetokens", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<TimeToken> getTimeTokens(@RequestParam(value = "max", required=false, defaultValue = "10") Long max){
		Flux<TimeToken> tokenFlux
				= Flux.fromStream(Stream.generate(()
								-> new TimeToken(System.currentTimeMillis(), "Flux-Token-"+System.currentTimeMillis())));
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		return Flux.zip(tokenFlux, interval)
				   .map(objects -> objects.getT1())
				   .take(max);
	}

}
