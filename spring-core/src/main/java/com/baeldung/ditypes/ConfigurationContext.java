package com.baeldung.ditypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.ditypes")
public class ConfigurationContext {

    @Bean
    public Host host(){
        return new Host("Jovem Nerd");
    }
    
    @Bean
    public Guest guest(){
        return new Guest("Azaghal");
    }
}
