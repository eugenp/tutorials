package com.baeldung.reactive.cors.global;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;

@SpringBootApplication(exclude = { MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoReactiveDataAutoConfiguration.class,
        MongoReactiveAutoConfiguration.class }
)
public class CorsGlobalConfigApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CorsGlobalConfigApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
        app.run(args);
    }

}
