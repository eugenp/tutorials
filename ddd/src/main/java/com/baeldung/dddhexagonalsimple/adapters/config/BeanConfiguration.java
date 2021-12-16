package com.baeldung.dddhexagonalsimple.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pizza.HexagonalPizza;
import pizza.adapters.persistence.PizzaRepository;
import pizza.domain.ports.PizzaService;

@Configuration
@ComponentScan(basePackageClasses = HexagonalPizza.class)
public class BeanConfiguration {

    @Bean
    PizzaService pizzaService(PizzaRepository repository) {
        return new PizzaService(repository, repository);
    }
}
