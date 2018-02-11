package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ComponentOne getComponentOne() {
        ComponentOne component = new ComponentOne();
        return component;
    }

}
