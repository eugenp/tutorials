package com.manoj.webFlux.client;

import com.manoj.webFlux.dto.TimeToken;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebFluxClient {

    @Bean
    WebClient webClient(){
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner runAsClient(WebClient webClient){
        return args -> {
            webClient.get()
                    .uri("/timetokens")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .retrieve()
                    .bodyToFlux(TimeToken.class)
                    .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebFluxClient.class)
                .properties("server.port=8585")
                .run(args);
    }

}
