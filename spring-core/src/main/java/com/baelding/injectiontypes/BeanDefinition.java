package com.baelding.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefinition {

    @Bean
    public UsefulBean usefulBean() {
        UsefulBean usefulBean = new UsefulBean("Hello", "Injection");
        return usefulBean;
    }

}
