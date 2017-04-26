package com.baeldung.spring_data_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.baeldung.spring_data.repository")
@EnableTransactionManagement
@EntityScan("com.baeldung.spring_data.model")
@SpringBootApplication
public class MainApp {
    public static void main(String[] args){
        SpringApplication.run(MainApp.class, args);
    }
}
