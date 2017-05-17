package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.example.Address;
import com.demo.example.Person;

@Configuration
public class DIConfiguration {

    @Bean
    public Person personBeanConfigurer(Address address) {
        Person person = new Person();
        person.setName("John");
        person.setAge(40);
        person.setAddress(address);
        return person;
    }

    @Bean
    public Address addressBeanConfigurer() {
        Address address = new Address();
        address.setCountry("India");
        return address;
    }

}
