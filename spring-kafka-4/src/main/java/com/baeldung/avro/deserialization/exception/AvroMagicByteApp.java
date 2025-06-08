package com.baeldung.avro.deserialization.exception;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.kafka.annotation.KafkaListener;

import com.baeldung.avro.deserialization.exception.avro.Article;

@SpringBootApplication
public class AvroMagicByteApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
            .sources(AvroMagicByteApp.class)
            .profiles("avro-magic-byte")
            .run(args);
    }

    @KafkaListener(topics = "test-topic")
    public void listen(Article article) {
        System.out.println("Received article: " + article);
    }

}