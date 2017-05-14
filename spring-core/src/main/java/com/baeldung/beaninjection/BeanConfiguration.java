package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.domain.ConsoleLogger;
import com.baeldung.beaninjection.domain.Logger;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class BeanConfiguration {

    @Bean
    public Logger logger() {
        return new ConsoleLogger();
    }

    @Bean
    public int maxChars() {
        return 15;
    }
    
    @Bean
    public String messagePrefix(){
        return "JConfig: ";
    }
}
