package org.baeldung.multipleauthproviders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ImportResource({ "classpath*:spring-security-multiple-auth-providers.xml" })
public class MultipleAuthProvidersApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleAuthProvidersApplication.class, args);
    }
}
