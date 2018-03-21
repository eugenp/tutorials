package com.baeldung.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.injectiontypes")
public class StudentConfig {

    @Bean
    public StudentDao studentDao() {

        return new StudentDao();
    }

}
