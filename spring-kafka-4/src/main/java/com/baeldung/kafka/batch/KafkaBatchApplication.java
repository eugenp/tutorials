package com.baeldung.kafka.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.countingmessages.Application;

@SpringBootApplication
public class KafkaBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
