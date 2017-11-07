package com.baeldung.injectbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.injectbean")
public class Config {

    @Bean
    public Address address() {
        return new Address("unknow street...");
    }

    @Bean
    public Employee employee() {
        return new Employee("Alex", 33);
    }

}
