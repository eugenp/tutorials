package org.baeldung.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {

    @Bean
    public Employee firstEmployee(){
        return new Employee("John");
    }

    @Bean
    @Primary
    public Employee secondEmployee(){
        return new Employee("Tony");
    }
}
