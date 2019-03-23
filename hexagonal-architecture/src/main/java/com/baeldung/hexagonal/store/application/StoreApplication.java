package com.baeldung.hexagonal.store.application;

import com.baeldung.hexagonal.store.infrastructure.persistence.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung.hexagonal.store.application", "com.baeldung.hexagonal.store.core"})
@Import({PersistenceConfig.class})
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
}
