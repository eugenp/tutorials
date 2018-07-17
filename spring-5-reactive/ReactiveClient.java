package vaibhav.com.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import vaibhav.com.model.Event;

import java.util.Collections;

@SpringBootApplication
public class ReactiveClient {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner consume(WebClient client) {
        return args -> {
            client.get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Event.class)
                .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveClient.class).properties(Collections.singletonMap("server.port", "8085"))
            .run(args);
    }
}
