package com.baeldung.springai.transcribe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-transcribe.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}