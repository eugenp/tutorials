package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.baeldung.dependencyinjectiontypeseval")
public class Config {

    @Bean
    @Scope("prototype")
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }

}
