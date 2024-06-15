package com.baeldung.hibernatejfr;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
public class HibernateJfrApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateJfrApplication.class,args);
    }
}
