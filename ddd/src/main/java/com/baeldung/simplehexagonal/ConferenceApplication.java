package com.baeldung.simplehexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.baeldung.simplehexagonal")
@PropertySource(value = { "classpath:simple-hexagonal.properties" })
public class ConferenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceApplication.class, args);
    }

}
