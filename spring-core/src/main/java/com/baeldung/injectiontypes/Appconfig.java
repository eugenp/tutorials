package com.baeldung.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.injectiontypes")
public class Appconfig {
    
    @Bean
    public Teacher getTeacher() {
        return new Teacher("Mr. Baeldung");
    }
    
    @Bean
    public int getPriority() {
        return 5;
    }
    
}
