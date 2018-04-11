package com.baeldung.reactive.eventstream.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Slf4j
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {
    @Autowired
    private WebClient webClient;

    public static void main(String[] args) {
        log.info("main");
        SpringApplication application = new SpringApplication(ClientApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        fromStream()
                .doOnNext(this::doSomethingOnArrival)
                .blockLast();
    }

    private void doSomethingOnArrival(Bar event) {
        log.info(event.toString());
    }

    private Flux<Bar> fromStream() {
        return webClient.get().uri("/foos")
                .header(HttpHeaders.CONTENT_TYPE, TEXT_EVENT_STREAM_VALUE)
                .retrieve()
                .bodyToFlux(Bar.class);
    }

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8989");
    }
}

class Bar {
    @Getter
    private Long id;
    @Getter
    private String name;

    @JsonCreator
    Bar(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + "-" + name;
    }
}
