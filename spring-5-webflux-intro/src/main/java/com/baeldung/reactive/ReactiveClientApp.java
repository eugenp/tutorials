package com.baeldung.reactive;

import java.time.Duration;

import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveClientApp {
    
    static {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
    }
    
    public static void main(String[] args) throws Exception {
        WebClient client = WebClient.create("http://localhost:8080");

        Flux<String> inChannel = 
            client.get()
            .uri("/events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(String.class);
        
        Disposable subs = inChannel.subscribe(System.out::println);

        Thread.sleep(10000);
        subs.dispose();
    }

}
