package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Customer Order Spring Boot Main Application
 *
 * */

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.*")
public class CustomerOrderApp
{

    public static void main( String[] args )
    {

        SpringApplication.run(CustomerOrderApp.class,args);

    }
}
