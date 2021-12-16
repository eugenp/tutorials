package com.baeldung.dddhexagonalsimple.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.baeldung.dddhexagonalsimple.HexagonalPizza;
import com.baeldung.dddhexagonalsimple.adapters.persistence.PizzaRepository;
import com.baeldung.dddhexagonalsimple.domain.PizzaService;

@Configuration
@ComponentScan(basePackageClasses = HexagonalPizza.class)
public class BeanConfiguration {

    @Bean
    PizzaService pizzaService(PizzaRepository repository) {
        return new PizzaService(repository, repository);
    }
}
