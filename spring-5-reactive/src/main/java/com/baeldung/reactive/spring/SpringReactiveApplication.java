package com.baeldung.reactive.spring;

import java.time.Duration;
import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
		
		ReactiveClient reactiveClient = new ReactiveClient();
    	reactiveClient.consume();
	}
}

@RestController
class ReactiveController {
	
    @GetMapping(value = "/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getAllmessages() {
        final Flux<String> flux = Flux
        		.interval(Duration.ofMillis(1000))
        		.map((interval) -> {
        			return (new StringBuilder("Time at: ")
        					.append(interval).append(" : ")
        					.append(Calendar.getInstance().getTime())
        					.toString());
        		});
        
        return flux;
    }
    
}

class ReactiveClient {

    WebClient client = WebClient.create("http://localhost:8080");
    
    public void consume() {
        
        Flux<String> messageFlux = client.get()
            .uri("/messages")
            .retrieve()
            .bodyToFlux(String.class);
        messageFlux.subscribe(System.out::println);
    }
    
}
