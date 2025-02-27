package com.baeldung.webflux.threadstarvation;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class ThreadStarvationApp {

    private static final Logger LOG = LogManager.getLogger(ThreadStarvationApp.class);

    public static void main(String[] args) {
        SpringApplication.run(ThreadStarvationApp.class);
    }

    @RestController
    class RestApi {

        @GetMapping("/blocking")
        Mono<String> getBlocking() {
            return Mono.fromCallable(() -> {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "foo";
            });
        }

        @GetMapping("/non-blocking")
        Mono<String> getNonBlocking() {
            return Mono.just("bar")
                .delayElement(Duration.ofSeconds(2));
        }

        @SuppressWarnings("BlockingMethodInNonBlockingContext")
        @GetMapping("/warning")
        Mono<String> warning() {
            Mono<String> data = fetchData();
            String response = "retrieved data: " + data.block();
            return Mono.just(response);
        }

        private Mono<String> fetchData() {
            return Mono.just("bar");
        }

        @GetMapping("/blocking-with-scheduler")
        Mono<String> getBlockingWithDedicatedScheduler() {
            return Mono.fromCallable(this::fetchDataBlocking)
                .subscribeOn(Schedulers.boundedElastic())
                .map(data -> "retrieved data: " + data);
        }

        private String fetchDataBlocking() {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "foo";
        }
    }

}
