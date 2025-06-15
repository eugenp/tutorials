package com.baeldung.avro.deserialization.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

import com.baeldung.avro.deserialization.exception.avro.Article;

@SpringBootApplication
class AvroMagicByteApp {

    private static final Logger LOG = LoggerFactory.getLogger(AvroMagicByteApp.class);

    final List<String> blog = new ArrayList<>();

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(AvroMagicByteApp.class)
            .profiles("avro-magic-byte")
            .run(args);
    }

    @KafkaListener(topics = "baeldung.article.published")
    public void listen(Article article) {
        LOG.info("a new article was published: {}", article);
        blog.add(article.getTitle());
    }

}