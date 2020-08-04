package com.baeldung.hexagonalarchitecture2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonalarchitecture2.adapters.InMemoryGameRepositoryAdapter;
import com.baeldung.hexagonalarchitecture2.core.RockPaperScissorsService;

@SpringBootApplication
public class HexagonalArchitecture2Application {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitecture2Application.class, args);
    }

}

@Configuration
class DomainConfiguration {

    @Bean
    public RockPaperScissorsService rockPaperScissorsService() {
        return new RockPaperScissorsService(new InMemoryGameRepositoryAdapter());
    }

}
