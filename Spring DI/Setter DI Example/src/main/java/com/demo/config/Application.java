package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.demo.example.Address;
import com.demo.example.Person;

@Configuration
@ComponentScan(basePackages = { "com.demo" })
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private InjectionValidator validator;

    @Override
    public void run(String... arg0) throws Exception {
        Person person = validator.getPerson();

        System.out.println("<------Spring Setter DI demo.------->");
        System.out.println("Person name: " + person.getName());
        System.out.println("Person age: " + person.getAge());

        Address address = person.getAddress();

        System.out.println("Person Country: " + address.getCountry());
        System.out.println("<-------------------------------------->");

    }
}
