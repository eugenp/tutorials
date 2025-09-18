package com.baeldung.jacksonlazyfields;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:com/baeldung/jacksonlazyfields/application.properties")
public class JacksonLazyFieldsApp {

    public static void main(String[] args) {
        SpringApplication.run(JacksonLazyFieldsApp.class, args);
    }
}
