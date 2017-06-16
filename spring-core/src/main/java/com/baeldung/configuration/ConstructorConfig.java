package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.model.College;
import com.baeldung.model.Student;

@Configuration
public class ConstructorConfig {

    @Bean
    public Student student() {
        return new Student(1, "John", college());
    }

    @Bean
    public College college() {
        return new College(1, "New York");
    }
}
