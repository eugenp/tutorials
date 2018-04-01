package com.baeldung.beaninjectiontypes.setterinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes.setterinjection")
public class Config {

    @Bean
    public Address address() {
        Address address = new Address();
        address.setCity("Los Angeles");
        address.setCountry("USA");
        return address;
    }

}
