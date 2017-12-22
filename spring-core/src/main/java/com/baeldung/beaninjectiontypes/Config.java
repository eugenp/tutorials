package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes")
public class Config {

    @Bean
    public ExampleService exampleService() {
        return new ExampleService();
    }

}
