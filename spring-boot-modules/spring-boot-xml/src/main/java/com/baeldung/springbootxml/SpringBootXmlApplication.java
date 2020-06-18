package com.baeldung.springbootxml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ImportResource("classpath:beans.xml")
public class SpringBootXmlApplication implements CommandLineRunner {

    @Autowired private Pojo pojo;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootXmlApplication.class, args);
    }

    public void run(String... args) {
        System.out.println(pojo.getField());
    }

}
