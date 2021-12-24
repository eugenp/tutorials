package com.baeldung.dddhexagonalsimple;

import com.baeldung.dddhexagonalsimple.domain.PizzaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.baeldung.dddhexagonalsimple")
@PropertySource(value = { "classpath:dddhexagonalsimple.yml" })
public class HexagonalPizza {
    public static void main(String[] args) {
        SpringApplication.run(HexagonalPizza.class, args);
    }
}
