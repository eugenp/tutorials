package com.baeldung.web.upload.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("com.baeldung.web.upload")
@SpringBootApplication
public class UploadApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(UploadApplication.class, args);
    }
}