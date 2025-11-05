package com.baeldung.application.orderbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(2)
public class ConfigTwo {

    @Bean
    public String configTwoBean() {
        return "ConfigTwoBean";
    }
}
