package com.baeldung.cognito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("cognito/application-cognito.yml")
public class SpringCognitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCognitoApplication.class, args);
    }
}
