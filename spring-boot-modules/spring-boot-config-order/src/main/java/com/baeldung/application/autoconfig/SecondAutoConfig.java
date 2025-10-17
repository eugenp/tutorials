package com.baeldung.application.autoconfig;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(FirstAutoConfig.class)
public class SecondAutoConfig {

    @Bean
    public String autoBeanTwo() {
        return "AutoBeanTwoAfterOne";
    }
}
