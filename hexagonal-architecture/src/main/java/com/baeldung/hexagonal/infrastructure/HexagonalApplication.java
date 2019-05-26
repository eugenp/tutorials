package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.domain.PersonPort;
import com.baeldung.hexagonal.domain.PersonRepositoryPort;
import com.baeldung.hexagonal.domain.PersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
    }

    @Bean
    PersonPort personPort(PersonRepositoryPort personRepositoryPort) {
        return new PersonService(personRepositoryPort);
    }
}
