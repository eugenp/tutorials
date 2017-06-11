package com.baeldung.displayallbeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.baeldung.displayallbeans.model.Person;

@Configuration
@ComponentScan(basePackages = "com.baeldung.displayallbeans")
public class SpringConfig {
    @Bean
    public Person person() {
        Person person = new Person();
        person.setName("Jon Doe");
        return person;
    }
}