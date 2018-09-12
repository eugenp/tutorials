package com.baeldung.reactive.webfluxevents.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@SpringBootApplication
public class ClientApplication {
    private static Logger logger = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplication.class)
          .web(WebApplicationType.NONE)
          .run(args);
    }

    @Bean
    public CommandLineRunner consumeFluxStream() {
        return args -> {
            Flux<ServerSentEvent<String>> fluxStream = WebClient.create("http://localhost:8080/sse")
              .get()
              .uri("/sse-flux")
              .retrieve()
              .bodyToFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {
              });

            fluxStream.subscribe(
              content ->
                logger.info("Current time: {} - {{}, id: {}, data: {}} ", LocalTime.now(), content.event(), content.id(), content.data()),
                error -> logger.error("Error receiving event: {}", error),
                () -> logger.info("Completed!!!"));

            //Let's block the execution so the Application continues to consume messages after initialization
            Thread.sleep(Duration.ofDays(1).toMillis());
        };
    }
}
