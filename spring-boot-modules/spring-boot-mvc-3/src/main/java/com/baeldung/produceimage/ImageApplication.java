package com.baeldung.produceimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("com.baeldung.produceimage")
public class ImageApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ImageApplication.class, args);
    }
}
