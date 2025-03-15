package com.baeldung.kafka.synchronous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@PropertySource("classpath:application-synchronous-kafka.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}