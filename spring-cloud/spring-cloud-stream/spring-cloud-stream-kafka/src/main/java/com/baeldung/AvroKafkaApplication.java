package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

@SpringBootApplication
@EnableBinding(Processor.class)
// The @EnableSchemaRegistryClient annotation needs to be uncommented to use the Spring native method.
// @EnableSchemaRegistryClient
public class AvroKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvroKafkaApplication.class, args);
    }

}
