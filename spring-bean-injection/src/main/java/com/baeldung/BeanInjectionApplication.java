package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@SpringBootApplication
@ComponentScan
@ImportResource("classpath:beans.xml")
public class BeanInjectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeanInjectionApplication.class, args);
    }
}
