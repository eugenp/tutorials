package com.baeldung.dddhexagonalsimple.adapters.config;

import com.baeldung.dddhexagonalsimple.adapters.persistence.PizzaOrderRepository;
import com.baeldung.dddhexagonalsimple.domain.PriceCalculator;
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
    PizzaService pizzaService(PizzaRepository repository, PizzaOrderRepository pizzaOrderRepository, PriceCalculator priceCalculator) {
        return new PizzaService(repository, repository, pizzaOrderRepository, priceCalculator);
    }

    @Bean
    PriceCalculator priceCalculator() {
        return new PriceCalculator();
    }
}
