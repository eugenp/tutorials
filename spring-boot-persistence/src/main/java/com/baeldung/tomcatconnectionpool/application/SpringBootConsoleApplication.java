package com.baeldung.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.baeldung.application"})
<<<<<<< HEAD
@EntityScan( basePackages = {"com.baeldung.application.entities"} )
=======
@EntityScan("com.baeldung.application.entities")
>>>>>>> 06fb834a56611876f0568b14299132e505903513
@EnableJpaRepositories("com.baeldung.application.repositories")
public class SpringBootConsoleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class);
    }
}
