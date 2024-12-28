package com.baeldung.spring.cloud.stream.rabbit.v2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.function.Function;

@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    @Bean
    public Function<String, String> enrichLogMessage() {
        return value -> "[%s] - %s".formatted("Baeldung", value);
    }

    @Bean
    public Function<String, String> highlightMessage() {
        return value -> value.toUpperCase();
    }

    @Bean
    public Function<String, Message<String>> processLogs() {
        return log -> {
            String destination = log.length() > 10? "enrichLogMessage-in-0" : "output-topic";
            return MessageBuilder.withPayload(log)
                    .setHeader("spring.cloud.stream.sendto.destination", destination)
                    .build();
        };
    }


}