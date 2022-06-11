package com.baeldung.MultipleBeanInstantiation.solution2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.MultipleBeanInstantiation.solution2.Person;

@Configuration
public class PersonConfig {

    @Bean
    @Qualifier("personOne")
    public Person personOne() {
        return new Person("Harold", "Finch");
    }

    @Bean
    @Qualifier("personTwo")
    public Person personTwo() {
        return new Person("John", "Reese");
    }
}