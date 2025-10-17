package com.baeldung.application.orderbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class ConfigOne {

    @Bean
    public String configOneBean() {
        return "ConfigOneBean";
    }
}
