package org.baeldung.spring.webflux.securityincidents;

import org.baeldung.spring.webflux.securityincidents.domain.SecurityEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        WebClient
            .builder()
            .build()
            .get()
            .uri("http://localhost:8080/securityeventsstream")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .flatMapMany(clientResponse -> clientResponse.bodyToFlux(SecurityEvent.class))
            .subscribe(securityEvent -> {
                System.out.println(securityEvent);
            });
    }
}
