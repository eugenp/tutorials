package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("2L", 6);
    }

    @Bean
    public Transmission transmission() {
        return new Transmission("step");
    }

    @Bean
    public Librarian librarian() {
        return new Librarian("sam");
    }
}
