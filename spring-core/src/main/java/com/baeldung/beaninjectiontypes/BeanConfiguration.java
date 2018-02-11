package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes")
@PropertySource("classpath:application.properties")
public class BeanConfiguration {

    @Bean
    public PropertyReader getServerLog() {
        return new PropertyReader();
    }

}
