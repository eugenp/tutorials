package com.baeldung.hibernate.onetomany.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ="com.baeldung.hibernate.onetomany.collection")
public class CollectionContextDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectionContextDemoApplication.class, args);
    }
}
