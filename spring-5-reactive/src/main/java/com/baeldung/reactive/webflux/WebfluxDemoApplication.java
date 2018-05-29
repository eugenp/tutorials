package com.baeldung.reactive.webflux;


import java.net.URI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebfluxDemoApplication {
     
    public static void main(String[] args) {
        SpringApplication.run(WebfluxDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandlineRunner(){
    	return args-> {
    		 URI streamdataUri = new URI("http://localhost:8080/event");   
		 WebClient client = WebClient.create();
		 Flux<ServerSentEvent<JsonNode>> events =client.get()
	                    						 .uri(streamdataUri)
	                    						 .accept(MediaType.TEXT_EVENT_STREAM)
	                    						 .exchange()
	                    						 .flatMapMany(r -> r.body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<JsonNode>>() {})));

		 
		 events.subscribe(System.out::println, Throwable::printStackTrace);
    	};
    }
   
}
