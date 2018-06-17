package com.baeldung.reactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveEventClient implements CommandLineRunner {
    public static void main(String[] args) {
        System.setProperty("server.port","8090");
        SpringApplication.run(ReactiveEventClient.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        WebClient webclient = WebClient.builder()
          .defaultHeader(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE)
          .baseUrl("http://localhost:8080/events")
          .build();

        webclient.get().uri("/stream/").header(HttpHeaders.CONTENT_TYPE,MediaType.TEXT_HTML.toString())
          .retrieve().bodyToFlux(String.class)
          .subscribe(System.out::println);
    }
}
