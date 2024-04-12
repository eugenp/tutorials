package com.baeldung.binder;

import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
class ProducerBinder {

    @Bean
    public Supplier output() {
        return () -> IntStream.range(1, 200)
          .mapToObj(ipSuffix -> "192.168.0." + ipSuffix)
          .map(entry -> MessageBuilder.withPayload(entry)
            .build());
    }
}