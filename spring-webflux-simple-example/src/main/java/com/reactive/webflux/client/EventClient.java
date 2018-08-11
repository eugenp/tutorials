package com.reactive.webflux.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(scanBasePackages = { "com.reactive" })
public class EventClient {

    Logger logger = LoggerFactory.getLogger(EventClient.class);

    @Bean
    public CommandLineRunner logEvents(WebClient client) {
        return args -> {
            client.get()
              .uri("/events")
              .accept(MediaType.TEXT_EVENT_STREAM)
              .retrieve()
              .bodyToFlux(String.class)
              .subscribe(msg -> {
                  logger.info(msg);
              });
            };
    }

    @Bean
    public WebClient getWebClient() {
        return WebClient.create("http://localhost:8081");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(EventClient.class).
          properties(java.util.Collections.singletonMap("server.port", "8081"))
          .run(args);
    }

}
