package com.baeldung.application.autoconfig;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureOrder(1)
public class FirstAutoConfig {

    @Bean
    public String autoBeanOne() {
        return "AutoBeanOne";
    }
}

