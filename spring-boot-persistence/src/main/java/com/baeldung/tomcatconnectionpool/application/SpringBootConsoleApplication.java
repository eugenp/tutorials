package com.baeldung.tomcatconnectionpool.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.baeldung.tomcatconnectionpool.application.repositories")
@ComponentScan(basePackages = {"com.baeldung.tomcatconnectionpool.application.*"})
@EntityScan("com.baeldung.tomcatconnectionpool.application.entities.*") 
public class SpringBootConsoleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class);
    }
}
