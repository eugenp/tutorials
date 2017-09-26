package com.baeldung.spring.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.baeldung.spring")
public class Config {

    @Bean
    public Cpu cpu() {
        return new Cpu("Intel-i7", "8GB", "2TB");
    }
 
    @Bean
    public Monitor monitor() {
        return new Monitor("21.5","1920 x 1080");
    }
        
        @Bean
    public Keyboard keyboard() {
        return new Keyboard("Logitech","Wireless");
    }
}
