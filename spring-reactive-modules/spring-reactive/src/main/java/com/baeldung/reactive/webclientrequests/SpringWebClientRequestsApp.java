package com.baeldung.reactive.webclientrequests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;

@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class SpringWebClientRequestsApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebClientRequestsApp.class, args);
    }
}
