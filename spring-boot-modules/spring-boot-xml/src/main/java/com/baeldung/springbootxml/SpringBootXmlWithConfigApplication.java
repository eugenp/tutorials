package com.baeldung.springbootxml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:beansconf.xml")
public class SpringBootXmlWithConfigApplication implements CommandLineRunner {

    @Autowired private Pojo pojo;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootXmlWithConfigApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(pojo.getField());
    }
}
