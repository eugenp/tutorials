package com.baeldung.beaninjectiontypes.constructorinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes.constructorinjection")
public class Config {

    @Bean
    public Address address() {
        return new Address("New York City", "USA");
    }

}
