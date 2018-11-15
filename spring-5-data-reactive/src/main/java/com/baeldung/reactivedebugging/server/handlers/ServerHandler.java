package com.baeldung.reactivedebugging.server.handlers;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactivedebugging.server.model.Foo;
import com.baeldung.reactivedebugging.server.repository.FooCrudRepository;
import com.mongodb.MongoException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ServerHandler {

	@Autowired
	FooCrudRepository repository;

	private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	public Mono<ServerResponse> useHandler(final ServerRequest request) {
		// there are chances that something goes wrong here...Foo 2 has 'null' name
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1)).flatMap(sequence -> {
			logger.info("retrieving Foo. Sequence: {}", sequence);
			if (ThreadLocalRandom.current().nextInt(0, 10) == 1) {
				throw new RuntimeException("There was an error retrieving the Foo!");
			}
			return repository.findById(1);

		}), Foo.class);
	}

	public Mono<ServerResponse> useHandlerFinite(final ServerRequest request) {
		// there are chances that something goes wrong here...Foo 2 has 'null' name
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.range(0, 10).map(sequence -> {
			return new Foo(sequence, "name" + sequence);
		}), Foo.class);
	}
}
