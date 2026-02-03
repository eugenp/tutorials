package com.baeldung.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = JtaAutoConfiguration.class)
@EnableJpaRepositories("com.baeldung.boot")
@EntityScan("com.baeldung.boot")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
