package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjectiontypes.models.Family;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes")
public class BeanConfig {

    @Bean
    public Family family() {
        return new Family("Ayo", "Kemi");
    }

}
