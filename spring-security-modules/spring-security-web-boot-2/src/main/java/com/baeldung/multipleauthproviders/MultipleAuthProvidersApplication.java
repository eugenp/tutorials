package com.baeldung.multipleauthproviders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-defaults.properties")
// @ImportResource({ "classpath*:spring-security-multiple-auth-providers.xml" })
public class MultipleAuthProvidersApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleAuthProvidersApplication.class, args);
    }
}
