package com.baeldung.beaninjection;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.beaninjection" })
public class TestConfig {

    /*@Bean
    public ComponentTwo componentTwo() {
        return new ComponentTwo();
    }
    
    @Bean
    public ComponentOne componentOne() {
        return new ComponentOne();
    }*/
}
