package com.example.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import com.example.Address;
import com.example.Person;

@SpringBootApplication
@ImportResource("classpath:spring-context.xml")
public class BaeldungApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(BaeldungApplication.class, args)
            .close();
        ;
    }

    @Override
    public void run(String... arg0) throws Exception {
        Person person = context.getBean(Person.class);

        System.out.println("<------Spring Setter DI demo.------->");
        System.out.println("Person name: " + person.getName());
        System.out.println("Person age: " + person.getAge());

        Address address = person.getAddress();

        System.out.println("Person Country: " + address.getCountry());
        System.out.println("<-------------------------------------->");

    }
}
