package com.baeldung.connectiondetails;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.connectiondetails")
public class ConnectionDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectionDetailsApplication.class, args);
    }

}
