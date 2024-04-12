package com.baeldung.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.startup")
public class SpringStartupConfig {

    @Bean(initMethod="init")
    public InitMethodExampleBean initMethodExampleBean() {
        return new InitMethodExampleBean();
    }
}