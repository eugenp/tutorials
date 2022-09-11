package com.baeldung.boot.collection.name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
@PropertySource("classpath:boot.collection.name/app.properties")
@EnableMongoRepositories(basePackages = { "com.baeldung.boot.collection.name" })
public class SpringBootCollectionNameApplication {
    public static void main(String... args) {
        SpringApplication.run(SpringBootCollectionNameApplication.class, args);
    }

    @Bean
    public Naming naming() {
        return new Naming();
    }
}
