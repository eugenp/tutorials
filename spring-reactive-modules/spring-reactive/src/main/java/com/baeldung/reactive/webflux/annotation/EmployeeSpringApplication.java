package com.baeldung.reactive.webflux.annotation;

import com.baeldung.reactive.webflux.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class EmployeeSpringApplication {

    @Bean
    EmployeeRepository employeeRepository() {
        return new EmployeeRepository();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeSpringApplication.class, args);

        EmployeeWebClient employeeWebClient = new EmployeeWebClient();
        employeeWebClient.consume();
    }

}
