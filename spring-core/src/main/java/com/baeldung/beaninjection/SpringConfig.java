package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class SpringConfig {

    @Bean
    public DbConnection dbConnection() {
        return new DbConnection();
    }

}
