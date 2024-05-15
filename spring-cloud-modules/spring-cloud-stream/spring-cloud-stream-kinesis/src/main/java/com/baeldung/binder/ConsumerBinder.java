package com.baeldung.binder;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerBinder {
    @Bean
    Consumer<String> input() {
        return str -> {
            System.out.println(str);
        };
    }
}