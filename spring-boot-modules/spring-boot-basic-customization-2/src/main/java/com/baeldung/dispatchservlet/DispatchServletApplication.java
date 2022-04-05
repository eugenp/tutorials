package com.baeldung.dispatchservlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class DispatchServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatchServletApplication.class, args);
    }

}
