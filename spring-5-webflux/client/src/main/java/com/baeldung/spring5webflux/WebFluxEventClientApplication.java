package com.baeldung.spring5webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class WebFluxEventClientApplication implements CommandLineRunner {
    @Autowired
    TaskExecutor executor;

    @Bean
    TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Override
    public void run(String... args) {
        WebFluxEventClient webFluxEventClient = new WebFluxEventClient("http://localhost:8080");
        executor.execute(webFluxEventClient::run);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebFluxEventClientApplication.class, args);
    }
}
