package com.baeldung.disablingkeycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.baeldung.disablingkeycloak" })
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
