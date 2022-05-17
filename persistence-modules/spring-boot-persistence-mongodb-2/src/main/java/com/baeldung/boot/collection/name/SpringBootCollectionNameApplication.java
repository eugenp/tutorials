package com.baeldung.boot.collection.name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:boot.collection.name/app.properties")
public class SpringBootCollectionNameApplication {
    public static void main(String... args) {
        SpringApplication.run(SpringBootCollectionNameApplication.class, args);
    }

    @Bean
    public Naming naming() {
        return new Naming();
    }
}
