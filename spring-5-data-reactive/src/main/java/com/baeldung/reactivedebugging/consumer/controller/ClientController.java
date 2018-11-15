package com.baeldung.reactivedebugging.consumer.controller;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactivedebugging.consumer.model.FooDao;
import com.baeldung.reactivedebugging.consumer.model.FooDto;
import com.baeldung.reactivedebugging.consumer.repository.FooCrudRepository;

import reactor.core.publisher.Flux;

@Component
public class ClientController {

	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	private WebClient client = WebClient.create("http://localhost:8081");

	@Autowired
	private FooCrudRepository repository;

//    @GetMapping("/launch-sse-client")
//    public String launchSSEFromSSEWebClient() {
//        consumeSSE();
//        return "LAUNCHED EVENT CLIENT!!! Check the logs...";
//    }

//	@GetMapping("/launch-flux-client")
//	public String launchcFluxFromSSEWebClient() {
//		consumeFlux();
//		return "LAUNCHED EVENT CLIENT!!! Check the logs...";
//	}
//	
//	@GetMapping("/launch-flux-client-2")
//	public String launchcFluxFromSSEWebClient2() {
//		consumeFlux2();
//		return "LAUNCHED EVENT CLIENT!!! Check the logs...";
//	}

//    @GetMapping("/launch-sse-from-flux-endpoint-client")
//    public String launchFluxFromFluxWebClient() {
//        consumeSSEFromFluxEndpoint();
//        return "LAUNCHED EVENT CLIENT!!! Check the logs...";
//    }

//    @Async
//    public void consumeSSE() {
//        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
//        };
//
//        Flux<ServerSentEvent<String>> eventStream = client.get()
//            .uri("/functional-reactive/periodic-foo")
//            .retrieve()
//            .bodyToFlux(type);
//
//        eventStream.subscribe(content -> logger.info("Current time: {} - Received SSE: name[{}], id [{}], content[{}] ", LocalTime.now(), content.event(), content.id(), content.data()), error -> logger.error("Error receiving SSE: {}", error),
//            () -> logger.info("Completed!!!"));
//    }

	@Scheduled(fixedRate = 60000)
	public void consumeFlux() {
		logger.info("Starting flux process 1...");
		Flux<FooDao> fooStream = client.get().uri("/functional-reactive/periodic-foo")
//            .accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve().bodyToFlux(FooDto.class).delayElements(Duration.ofMillis(100)).map(FooDao::new);
		repository.saveAll(fooStream);
	}

//		repository.saveAll(fooStream);

//		fooStream.(receivedFoo -> {
//			
//			logger.info("Current time: {} - Received content: {}. Setting index {} ", LocalTime.now(), receivedFoo, index);
//			index += 1;
//			receivedFoo.setId(index);
//			repository.save(receivedFoo);
//		}, error -> logger.error("Error retrieving content: {}", error), () -> logger.info("Completed!!!"));
//	}

	@Scheduled(fixedRate = 70000)
	public void consumeFlux2() {
		logger.info("Starting flux process 2...");
		client.get().uri("/functional-reactive/periodic-foo-2")
            .accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve().bodyToFlux(FooDto.class).map(dto -> {
					Integer random = ThreadLocalRandom.current().nextInt(0, 20);
					logger.info("process 2 with dto id {} name{} - random: {}", dto.getId(), dto.getName(), random);
					Integer quantity = dto.getId() / random;
					return new FooDao(dto.getId(),  dto.getName(), quantity);
				}).subscribe(repository::save);
//		repository.saveAll(fooStream);
	}

	@Scheduled(fixedRate = 70000)
	public void consumeFlux3() {
		logger.info("Starting flux process 3...");
		Flux<FooDao> fooStream = client.get().uri("/functional-reactive/periodic-foo-2")
//            .accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve().bodyToFlux(FooDto.class).map(dto -> {
					String formattedName = dto.getName().substring(2, 4);
					logger.info("process 3 with dto id {} name{}", dto.getId(), dto.getName());
					return new FooDao(dto.getId(),  formattedName, 1);
				});
		repository.saveAll(fooStream);
	}

//    @Async
//    public void consumeSSEFromFluxEndpoint() {
//        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
//        };
//
//        Flux<ServerSentEvent<String>> eventStream = client.get()
//            .uri("/stream-flux")
//            .accept(MediaType.TEXT_EVENT_STREAM)
//            .retrieve()
//            .bodyToFlux(type);
//
//        eventStream.subscribe(content -> logger.info("Current time: {} - Received SSE: name[{}], id [{}], content[{}] ", LocalTime.now(), content.event(), content.id(), content.data()), error -> logger.error("Error receiving SSE: {}", error),
//            () -> logger.info("Completed!!!"));
//    }
}
