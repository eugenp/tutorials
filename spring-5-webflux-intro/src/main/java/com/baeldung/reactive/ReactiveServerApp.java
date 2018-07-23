package com.baeldung.reactive;

import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableWebFlux
public class ReactiveServerApp {
    
    @RestController
    public static class EventEmitter {
        
        @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<String> getEvents() {
            Stream<String> eventStream = Stream.generate(() -> {
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               
               return "an event!";
            });
            
            return Flux.fromStream(eventStream);
        }
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ReactiveServerApp.class);
    }
}
