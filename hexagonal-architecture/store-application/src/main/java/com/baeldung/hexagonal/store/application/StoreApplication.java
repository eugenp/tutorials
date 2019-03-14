package com.baeldung.hexagonal.store.application;

import com.baeldung.hexogonal.store.emailsender.EmailSenderConfig;
import com.baeldung.hexagonal.store.persistence.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung.hexogonal.store.application","com.baeldung.hexogonal.store.core"})
@Import({PersistenceConfig.class, EmailSenderConfig.class})
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
}
